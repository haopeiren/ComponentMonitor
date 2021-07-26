package com.ffcs.controller;

import com.ffcs.model.MQInstance;
import com.ffcs.response.ListMQInstanceResp;
import com.ffcs.service.MQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MQController
 *
 * @author haopeiren
 * @since 2021/7/19
 */
@Api(tags = "消息中间件")
@RestController
public class MQController {
    @Autowired
    private MQService mqService;

    /**
     * 查询MQ实例列表
     *
     * @param limit 查询数目
     * @param offset    偏移量
     * @return  实例列表
     */
    @ApiOperation(value = "查询MQ实例列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "查询条数"),
            @ApiImplicitParam(name = "offset", value = "偏移量")
    })
    @GetMapping(value = "/mq/instances")
    public ListMQInstanceResp listMQInstance(Integer limit, Integer offset) {
        List<MQInstance> mqInstanceVOList = mqService.listMQInstance(limit, offset);
        ListMQInstanceResp resp = new ListMQInstanceResp();
        resp.setInstances(mqInstanceVOList);
        return resp;
    }

    /**
     * 同步MQ实例最新信息
     */
    @ApiOperation(value = "同步MQ实例信息")
    @PostMapping(value = "/mq/instances/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshMQInstance() {
        mqService.syncMQInstanceInfo();
    }
}
