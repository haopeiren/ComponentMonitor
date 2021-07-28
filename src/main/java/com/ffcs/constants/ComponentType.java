package com.ffcs.constants;

/**
 * 组件常量枚举值
 *
 * @author haopeiren
 * @since 2021/7/15
 */
public interface ComponentType {
    /**
     * 高可用数据库
     */
    int TELE_DB = 1;

    /**
     * 分布式数据库
     */
    int DUAL = 2;

    /**
     * PostGreSQL数据库
     */
    int POSTGRE_SQL = 3;

    /**
     * 分布式缓存
     */
    int CACHE = 4;

    /**
     * ElasticSearch
     */
    int ES = 5;

    /**
     * 命名空间
     */
    int NAMESPACE = 6;

    /**
     * 消息中间件
     */
    int MQ = 7;

    /**
     * 负载均衡
     */
    int SLB = 8;

    /**
     * 分布式文件系统
     */
    int DFS = 9;

    /**
     * 分布式容器管理
     */
    int CCSE = 10;
}
