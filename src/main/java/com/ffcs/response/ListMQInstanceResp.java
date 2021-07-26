package com.ffcs.response;

import com.ffcs.model.MQInstance;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * ListMQInstanceResp
 *
 * @author haopeiren
 * @since 2021/7/19
 */
@Data
@ApiModel(value = "消息中间件列表响应体")
public class ListMQInstanceResp {
    private List<MQInstance> instances;
}
