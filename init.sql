CREATE TABLE `host` (
    `id`    int(11)         AUTO_INCREMENT  COMMENT 'id',
    `ip`    varchar(20)     NOT NULL    UNIQUE    COMMENT 'ip',
    `type`  int(10)         NOT NULL    COMMENT '1表示物理机， 2表示虚拟机',
    `name`  varchar(64)     NOT NULL    COMMENT '主机名',
    PRIMARY KEY(`id`),
    UNIQUE(`ip`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主机表';

CREATE TABLE `component` (
    `id` int(11)    NOT NULL 'id',
    `name`  varchar(64) NOT NULL '组件名',
    `value` int(11)     NOT NULL `value`,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='';

CREATE TABLE `components` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `connect_type` int(11) NOT NULL COMMENT '连接类型 1表示MYSQL',
  `ip` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'ip',
  `port` int(11) NOT NULL COMMENT 'port',
  `username` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `component_id` int(11) NOT NULL COMMENT '组件类型 1MQ',
  `database` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据库名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `mq_cluster` (
  `id` int(20) NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL COMMENT '租户ID',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `prod_specid` varchar(255) NOT NULL DEFAULT 'default' COMMENT '产品规格ID',
  `out_userid` varchar(255) DEFAULT NULL COMMENT '外部用户ID',
  `vpc_id` varchar(255) DEFAULT NULL COMMENT 'vpcId',
  `brokerClusterName` varchar(100) NOT NULL COMMENT 'broker集群名',
  `namesrvClusterName` varchar(100) NOT NULL COMMENT 'namesrv集群名',
  `clusterInstanceName` varchar(100) DEFAULT NULL COMMENT '实例名',
  `ssh_info` varchar(1000) DEFAULT NULL,
  `path_perfix` varchar(255) NOT NULL COMMENT '主目录',
  `version` varchar(20) NOT NULL COMMENT '版本',
  `zk_address` varchar(2000) NOT NULL COMMENT 'zk地址',
  `zk_ssl` varchar(10) DEFAULT '0' COMMENT 'zkssl选项',
  `order_id` varchar(255) DEFAULT NULL COMMENT '关联order_id',
  `state` int(10) DEFAULT '0' COMMENT '状态0初始1正常2冻结3停机',
  `prop` text COMMENT '其他属性json',
  `crt_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `bak_int_1` int(10) DEFAULT '0' COMMENT 'int备用1',
  `bak_int_2` int(10) DEFAULT '0' COMMENT 'int备用2',
  `bak_varchar_1` varchar(255) DEFAULT NULL COMMENT 'varchar备用1',
  `bak_varchar_2` varchar(255) DEFAULT NULL COMMENT 'varchar备用2',
  `ipv6_flag` varchar(10) DEFAULT '0' COMMENT 'ipv6 flag',
  `delete` int(10) DEFAULT '0' COMMENT '该实例是否已删除',
  `sync_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发现时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='集群表';