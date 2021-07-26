package com.ffcs.controller;

import com.ffcs.enums.ErrorCode;
import com.ffcs.exception.ServiceException;
import com.ffcs.model.Host;
import com.ffcs.service.HostService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

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
    @PostMapping(value = "/hosts/import")
    public void importHostData(@RequestParam("file") MultipartFile dataFile) {
        hostService.importHostData(dataFile);
    }

    @ApiOperation(value = "查询主机列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "查询条数"),
            @ApiImplicitParam(name = "offset", value = "偏移量"),
            @ApiImplicitParam(name = "ip", value = "ip"),
            @ApiImplicitParam(name = "name", value = "主机名")
    })
    @GetMapping(value = "/hosts")
    public List<Host> listHost(
            @RequestParam @Min(10) Integer limit,
            @RequestParam @Min(0) Integer offset,
            @RequestParam(required = false) @Length(max = 64) String ip,
            String name) {
        return hostService.listHost(limit, offset, ip, name);
    }

    @ApiOperation(value = "下载导入模板")
    @GetMapping(value = "/hosts/templates/download")
    public void downloadHostTemplate(HttpServletResponse response) {
        try {
            hostService.downloadHostTemplate(response);
        } catch (IOException e) {
            log.error("failed to download template", e);
            throw new ServiceException(ErrorCode.DOWNLOAD_FILE_EXCEPTION);
        }
    }
}