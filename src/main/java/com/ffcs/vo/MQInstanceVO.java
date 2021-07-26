package com.ffcs.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * ListMQInstanceVO
 *
 * @author haopeiren
 * @since 2021/7/19
 */
@Data
@ApiModel(value = "消息中间件实例信息")
public class MQInstanceVO {
    private Integer id;

    private String brokerClusterName;

    private String namesrvClusterName;

    private String clusterInstanceName;

    private String version;
}
