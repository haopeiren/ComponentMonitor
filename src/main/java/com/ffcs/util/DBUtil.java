package com.ffcs.util;

import com.ffcs.annotation.TableField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DB工具类
 *
 * @author haopeiren
 * @since 2021/7/16
 */
@Slf4j
public class DBUtil {
    /**
     * 从ResultSet中获取数据列表
     *
     * @param rs 查询结果
     * @param cls   Class
     * @param <T>   返回的结果类型，该类型的属性必须用{@link com.ffcs.annotation.TableField}修饰，否则填充属性时会被忽略
     * @return  转换后的数据列表
     * @throws SQLException SQLException
     * @throws ReflectiveOperationException ReflectiveOperationException
     */
    public static <T> List<T> getResult(ResultSet rs, Class<T> cls) throws SQLException, ReflectiveOperationException {
        List<T> resultList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        while (rs.next()) {
            T t = newInstance(cls);
            for (Field curField : fields) {
                setValue(t, rs, curField);
            }
            resultList.add(t);
        }
        return resultList;
    }

    private static <T> void setValue(T target, ResultSet rs, Field field) throws ReflectiveOperationException, SQLException {
        TableField tableField = field.getAnnotation(TableField.class);
        if (Objects.isNull(tableField)) {
            return;
        }
        String columnName = tableField.columnName();
        if (StringUtils.isEmpty(columnName)) {
            columnName = field.getName();
        }
        field.setAccessible(true);
        switch (tableField.fieldType()) {
            case INTEGER:
                field.set(target, rs.getInt(columnName));
                break;
            case STRING:
                field.set(target, rs.getString(columnName));
                break;
            case DATE:
                field.set(target, rs.getDate(columnName));
                break;
            case TIMESTAMP:
                field.set(target, rs.getTimestamp(columnName));
        }
    }

    private static <T> T newInstance(Class<T> cls) throws ReflectiveOperationException {
        T instance;
        try {
            instance = cls.newInstance();
        } catch (ReflectiveOperationException e) {
            log.error("Failed to create a JavaBean. Make sure you provide a public, parameterless constructor ");
            throw e;
        }
        return instance;
    }
}
