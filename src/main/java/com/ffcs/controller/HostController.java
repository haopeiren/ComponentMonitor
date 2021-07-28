package com.ffcs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ffcs.dto.HostListDto;
import com.ffcs.model.Host;
import com.ffcs.service.impl.HostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * 主机controller
 *
 * @author haopeiren
 * @since 2021/7/23
 */
@Api(tags = "主机")
@RestController
@Slf4j
@Validated
public class HostController {
    @Autowired
    private HostService hostService;

    @ApiOperation(value = "导入主机列表")
    @PostMapping(value = "/hosts")
    public void importHostData(@RequestBody HostListDto hostList) {
        hostService.importHosts(hostList);
    }

    @ApiOperation(value = "查询主机列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页大小"),
            @ApiImplicitParam(name = "ip", value = "ip"),
    })
    @GetMapping(value = "/hosts")
    public IPage<Host> listHost(
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(1) Integer size,
            @RequestParam(required = false) @Length(max = 64) String ip) {
        return hostService.listHost(page, size, ip);
    }
}