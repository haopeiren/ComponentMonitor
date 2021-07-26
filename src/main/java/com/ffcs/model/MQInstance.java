package com.ffcs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ffcs.annotation.TableField;
import com.ffcs.annotation.FieldType;
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
@Data
@EqualsAndHashCode(of = "id")
@ApiModel(value = "MQ实例详情")
public class MQInstance {
    @TableField(fieldType = FieldType.INTEGER)
    private Integer id;

    @TableField(fieldType = FieldType.STRING, columnName = "tenant_id")
    private String tenantId;

    @TableField(fieldType = FieldType.STRING, columnName = "user_id")
    private String userId;

    @TableField(fieldType = FieldType.STRING, columnName = "prod_specid")
    private String prodSpecId;

    @TableField(fieldType = FieldType.STRING, columnName = "out_userid")
    private String outUserId;

    @TableField(fieldType = FieldType.STRING, columnName = "vpc_id")
    private String vpcId;

    @TableField(fieldType = FieldType.STRING, columnName = "brokerClusterName")
    private String brokerClusterName;

    @TableField(fieldType = FieldType.STRING, columnName = "namesrvClusterName")
    private String namesrvClusterName;

    @TableField(fieldType = FieldType.STRING, columnName = "clusterInstanceName")
    private String clusterInstanceName;

    @TableField(fieldType = FieldType.STRING, columnName = "ssh_info")
    private String sshInfo;

    @TableField(fieldType = FieldType.STRING, columnName = "path_perfix")
    private String pathPerfix;

    @TableField(fieldType = FieldType.STRING)
    private String version;

    @TableField(fieldType = FieldType.STRING, columnName = "zk_address")
    private String zkAddress;

    @TableField(fieldType = FieldType.STRING, columnName = "zk_ssl")
    private String zkSsl;

    @TableField(fieldType = FieldType.STRING, columnName = "order_id")
    private String orderId;

    @TableField(fieldType = FieldType.INTEGER, columnName = "state")
    private Integer state;

    @TableField(fieldType = FieldType.STRING, columnName = "prop")
    private String prop;

    @TableField(fieldType = FieldType.TIMESTAMP, columnName = "crt_time")
    private Timestamp crtTime;

    @TableField(fieldType = FieldType.TIMESTAMP, columnName = "mod_time")
    private Timestamp modTime;

    @TableField(fieldType = FieldType.INTEGER, columnName = "bak_int_1")
    private Integer bakInt1;

    @TableField(fieldType = FieldType.INTEGER, columnName = "bak_int_2")
    private Integer bakInt2;

    @TableField(fieldType = FieldType.STRING, columnName = "bak_varchar_1")
    private String bakVarchar1;

    @TableField(fieldType = FieldType.STRING, columnName = "bak_varchar_2")
    private String bakVarchar2;

    @TableField(fieldType = FieldType.STRING, columnName = "ipv6_flag")
    private String ipv6Flag;

    private Integer delete;

    private Timestamp syncTime;
}
