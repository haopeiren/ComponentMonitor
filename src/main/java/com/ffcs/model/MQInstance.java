package com.ffcs.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * MQ实例信息
 *
 * @author haopeiren
 * @since 2021/7/15
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MQ实例详情")
@Data
@TableName(value = "mq_cluster")
public class MQInstance extends BaseInstance {
    private String tenantId;

    private String userId;

    private String prodSpecid;

    private String outUserid;

    private String vpcId;

    @TableField(value = "brokerClusterName")
    private String brokerClusterName;

    @TableField(value = "namesrvClusterName")
    private String namesrvClusterName;

    @TableField(value = "clusterInstanceName")
    private String clusterInstanceName;

    private String sshInfo;

    private String pathPerfix;

    private String version;

    private String zkAddress;

    private String zkSsl;

    private String orderId;

    private Integer state;

    private String prop;

    private Timestamp crtTime;

    private Timestamp modTime;

    @TableField(value = "bak_int_1")
    private Integer bakInt1;

    @TableField(value = "bak_int_2")
    private Integer bakInt2;

    @TableField(value = "bak_varchar_1")
    private String bakVarchar1;

    @TableField(value = "bak_varchar_2")
    private String bakVarchar2;

    private String ipv6Flag;
}
