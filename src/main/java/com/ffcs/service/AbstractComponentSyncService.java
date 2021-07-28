package com.ffcs.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ffcs.constants.EnvironmentType;
import com.ffcs.model.BaseInstance;
import com.ffcs.model.ComponentDBInfo;
import com.ffcs.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author haopeiren
 * @since 2021/7/27
 */
@Slf4j
public abstract class AbstractComponentSyncService<M extends BaseMapper<T>, T extends BaseInstance> extends ServiceImpl<M,T> implements IComponentSyncService{
    private static final String DEFAULT_VALIDATION_SQL = "SELECT 1 FROM DUAL";

    private volatile boolean dataSourceInitialed = false;

    private final Object lock = new Object();

    private DataSource prodDataSource;

    private DataSource devDataSource;

    private String tableName;

    private String columns;

    private Class<T> resultType;

    public AbstractComponentSyncService(String tableName, String columns, Class<T> resultType) {
        this.tableName = tableName;
        this.columns = columns;
        this.resultType = resultType;
    }

    /**
     * 同步组件信息
     *
     * @param dbInfoList 组件实例信息所在的数据库信息
     */
    @Override
    public void syncComponentInstances(List<ComponentDBInfo> dbInfoList) {
        initDataSource(dbInfoList);
        List<T> instanceList = queryInstances();
        saveInstance(instanceList);
    }

    private List<T> queryInstances() {
        List<T> prodInstanceList = executeQuery(prodDataSource, EnvironmentType.PRODUCT);
        List<T> devInstanceList = executeQuery(devDataSource, EnvironmentType.DEVELOP);
        List<T> unionList = new ArrayList<>(prodInstanceList);
        unionList.addAll(devInstanceList);
        return unionList;
    }

    /**
     * 保存组件实例信息
     *
     * @param instanceList 实例列表
     */
    public abstract void saveInstance(List<T> instanceList);

    private void initDataSource(List<ComponentDBInfo> componentDBInfoList) {
        if (dataSourceInitialed) {
            return;
        }
        synchronized (lock) {
            if (dataSourceInitialed) {
                return;
            }
            Map<Integer, ComponentDBInfo> infoMap = componentDBInfoList.stream()
                    .collect(Collectors.toMap(ComponentDBInfo::getEnvType, Function.identity()));
            ComponentDBInfo prodInfo = infoMap.get(EnvironmentType.PRODUCT);
            if (Objects.nonNull(prodInfo)) {
                prodDataSource = initDataSource(prodInfo);
            }
            ComponentDBInfo devInfo = infoMap.get(EnvironmentType.DEVELOP);
            if (Objects.nonNull(devInfo)) {
                devDataSource = initDataSource(devInfo);
            }
            dataSourceInitialed = true;
        }
    }

    private DataSource initDataSource(ComponentDBInfo info) {
        DruidDataSource dataSource = new DruidDataSource();
        String url = CommonConstants.DB_MYSQL_URL
                .replace("{IP}", info.getIp())
                .replace("{PORT}", String.valueOf(info.getPort()))
                .replace("{DATABASE}", info.getDbName());
        dataSource.setUrl(url);
        dataSource.setDriverClassName(CommonConstants.DB_MYSQL_DRIVER);
        dataSource.setUsername(info.getUserName());
        dataSource.setPassword(info.getUserPass());
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setValidationQuery(DEFAULT_VALIDATION_SQL);
        return dataSource;
    }

    private List<T> executeQuery(DataSource dataSource, int environment) {
        if (Objects.isNull(dataSource)) {
            log.warn("dataSource is empty, environment : {}", environment);
            return Collections.emptyList();
        }
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT " + columns + " FROM " + tableName;
        List<T> infoList = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                T instance = resolveRow(rs, environment);
                infoList.add(instance);
            }
        } catch (SQLException sqlException) {
            log.error("failed to query MQ info from database", sqlException);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            //TODO 是否真实关闭了connection
            closeQuietly(connection);
        }
        return infoList;
    }

    private T resolveRow(ResultSet resultSet, int environment) throws ReflectiveOperationException, SQLException {
        T obj = resultType.newInstance();
        // 填充BaseInstance属性
        obj.setId(resultSet.getInt("id"));
        obj.setEnvType(environment);

        Field[] fields = resultType.getDeclaredFields();
        for (Field field : fields) {
            setObjectValue(obj, field, resultSet);
        }
        return obj;
    }

    private void setObjectValue(T target, Field field, ResultSet rs) throws SQLException, ReflectiveOperationException {
        String fieldName = field.getName();
        String fieldTypeName = field.getType().getSimpleName();
        Object value = null;
        switch (fieldTypeName) {
            case "String" :
                value = rs.getString(fieldName);
                break;
            case "Integer":
            case "int":
                value = rs.getInt(fieldName);
                break;
            case "Timestamp":
                value = rs.getTimestamp(fieldName);
                break;
            default:
                log.error("unSupported field type, {}:{}", target.getClass(), fieldName);
        }
        field.setAccessible(true);
        field.set(target, value);
    }

    private void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.info("close connection failed", e);
            }
        }
    }
}
