package com.ffcs.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.ffcs.constants.ComponentType;
import com.ffcs.mapper.ComponentConnectInfoMapper;
import com.ffcs.mapper.MQMapper;
import com.ffcs.model.ComponentConnectInfo;
import com.ffcs.model.MQInstance;
import com.ffcs.util.CommonConstants;
import com.ffcs.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MQService
 *
 * @author haopeiren
 * @since 2021/7/15
 */
@Service
@Slf4j
public class MQService {
    private static final String MQ_CLUSTER_TABLE = "mq_cluster";

    private static final String MQ_CLUSTER_COLUMNS = "id, tenant_id, user_id, prod_specid, out_userid, vpc_id, brokerClusterName, namesrvClusterName, clusterInstanceName, ssh_info, path_perfix, version, zk_address, zk_ssl, order_id, `state`, prop, crt_time, mod_time, bak_int_1, bak_int_2, bak_varchar_1, bak_varchar_2, ipv6_flag";

    private final Object lock = new Object();

    private DruidDataSource dataSource;

    private volatile boolean dataSourceInitialed = false;

    @Autowired
    private ComponentConnectInfoMapper mapper;

    @Autowired
    private MQMapper mqMapper;

    /**
     * 查询MQ实例列表
     *
     * @param limit 查询条数
     * @param offset    偏移量
     * @return  实例列表
     */
    public List<MQInstance> listMQInstance(Integer limit, Integer offset) {
        List<MQInstance> instanceList = mqMapper.queryMQInstanceList(limit, offset);
        return instanceList;
    }

    /**
     * 同步MQ实例信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncMQInstanceInfo() {
        ComponentConnectInfo info = mapper.getConnectionInfo(ComponentType.MQ);
        if (!dataSourceInitialed) {
            initDataSource(info);
        }
        List<MQInstance> instanceList = getMQInstanceList();
        mqMapper.saveMQInstance(instanceList);
    }

    private void saveData1(List<MQInstance> instanceList) {
        List<MQInstance> dbInstanceList = mqMapper.queryMQInstanceList(null, null);
        List<MQInstance> remainInstanceList = dbInstanceList
                .stream()
                .filter(item -> item.getDelete().equals(0))
                .collect(Collectors.toList());
        // 新增的实例
        List<MQInstance> newInstanceList = instanceList
                .stream()
                .filter(item -> !remainInstanceList.contains(item))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(newInstanceList)) {
            List<Integer> newInstanceIdList = newInstanceList
                    .stream()
                    .map(MQInstance::getId)
                    .collect(Collectors.toList());
            log.info("there are {} instance to be added, {}", newInstanceList.size(), newInstanceIdList);
            mqMapper.saveMQInstance(newInstanceList);
        }

        // 删除的实例
        List<MQInstance> deleteInstanceList = remainInstanceList
                .stream()
                .filter(item -> !instanceList.contains(item))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteInstanceList)) {
            List<Integer> deleteInstanceIdList = deleteInstanceList
                    .stream()
                    .map(MQInstance::getId)
                    .collect(Collectors.toList());
            log.info("there are {} instance to be deleted, {}", deleteInstanceList.size(), deleteInstanceIdList);
            mqMapper.deleteMQInstance(deleteInstanceIdList);
        }
        // TODO 需要更新状态的实例
//        List<MQInstance> updateInstanceList = new ArrayList<>();
//        List<MQInstance> bothList = instanceList
//                .stream()
//                .filter(remainInstanceList::contains)
//                .collect(Collectors.toList());
//        Map<Integer, MQInstance> instanceMap = instanceList
//                .stream()
//                .collect(Collectors.toMap(MQInstance::getId, Function.identity()));
//        bothList.forEach(item -> {
//            Integer id = item.getId();
//            if (item.getState().equals(instanceMap.get(id).getState())) {
//                updateInstanceList.add(item);
//            }
//        });
    }

    private List<MQInstance> getMQInstanceList() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT " + MQ_CLUSTER_COLUMNS + " FROM " + MQ_CLUSTER_TABLE;
        List<MQInstance> infoList = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            infoList = DBUtil.getResult(rs, MQInstance.class);
        } catch (SQLException sqlException) {
            log.error("failed to query MQ info from database", sqlException);
        } catch (ReflectiveOperationException ex) {
            log.error("failed to parse data from query result");
        }
        finally {
            closeQuietly(rs);
            closeQuietly(ps);
            //TODO 是否真实关闭了connection
            closeQuietly(connection);
        }
        return infoList;
    }

    private void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.info("close connection failed");
            }
        }
    }

    private void initDataSource(ComponentConnectInfo info) {
        if (dataSourceInitialed) {
            return;
        }
        synchronized (lock) {
            if (dataSourceInitialed) {
                return;
            }
            dataSource = new DruidDataSource();
            String url = CommonConstants.DB_MYSQL_URL
                    .replace("{IP}", info.getIp())
                    .replace("{PORT}", String.valueOf(info.getPort()))
                    .replace("{DATABASE}", info.getDatabase());
            dataSource.setUrl(url);
            dataSource.setDriverClassName(CommonConstants.DB_MYSQL_DRIVER);
            dataSource.setUsername(info.getUsername());
            dataSource.setPassword(info.getPassword());
            dataSourceInitialed = true;
        }
    }

}
