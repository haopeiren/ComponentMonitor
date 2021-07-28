package com.ffcs.datasource;

/**
 * DataSourceHolder
 *
 * @author haopeiren
 * @since 2021/7/27
 */
public class DataSourceHolder {
    private static ThreadLocal<Integer> dataSourceKey = new ThreadLocal<>();

    public static void setDataSourceKey(Integer key) {
        dataSourceKey.set(key);
    }

    public static Integer getDataSourceKey() {
        return dataSourceKey.get();
    }

    public static void clear() {
        dataSourceKey.remove();
    }
}
