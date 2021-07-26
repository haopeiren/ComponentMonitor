package com.ffcs.service;

import com.ffcs.enums.ErrorCode;
import com.ffcs.enums.HostType;
import com.ffcs.exception.ServiceException;
import com.ffcs.mapper.HostMapper;
import com.ffcs.model.Host;
import com.ffcs.util.CommonConstants;
import com.ffcs.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.PathResourceResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * HostService
 *
 * @author haopeiren
 * @since 2021/7/23
 */
@Service
@Slf4j
public class HostService {
    @Autowired
    private HostMapper hostMapper;

    /**
     * 查询主机列表
     *
     * @param limit     分页大小
     * @param offset    偏移量
     * @param ip        ip
     * @param name      name
     * @return  查询结果
     */
    public List<Host> listHost(Integer limit, Integer offset, String ip, String name) {
        if (Objects.isNull(limit)) {
            limit = CommonConstants.DEFAULT_PAGE_LIMIT;
        }
        if (Objects.isNull(offset)) {
            offset = CommonConstants.DEFAULT_PAGE_OFFSET;
        }
        return hostMapper.listHost(limit, offset, ip, name);
    }

    /**
     * 导入host列表
     *
     * @param dataFile host文件表格
     */
    public void importHostData(MultipartFile dataFile) {
        List<Map<String, String>> excelData = getData(dataFile);
        List<Host> hostList = getHost(excelData);
        if (!CollectionUtils.isEmpty(hostList)) {
            int res = hostMapper.saveHost(hostList);
            log.info("{} records were successfully imported", res);
        }
    }

    private List<Host> getHost(List<Map<String, String>> excelData) {
        List<Host> hostList = new ArrayList<>();
        excelData.forEach(item -> {
            Host host = new Host();
            host.setId(Integer.parseInt(item.get("id")));
            String type = item.get("type");
            int value;
            if (HostType.PHYSICAL.getName().equals(type)) {
                value = HostType.PHYSICAL.getValue();
            } else {
                value = HostType.VIRTUAL.getValue();
            }
            host.setType(value);
            host.setIp(item.get("ip"));
            host.setName(item.get("name"));
            hostList.add(host);
        });
        return hostList;
    }

    private List<Map<String, String>> getData(MultipartFile file) {
        List<Map<String, String>> excelData;
        try {
            excelData = ExcelUtil.parseExcel(file.getInputStream());
        } catch (IOException e) {
            log.error("failed to read file content");
            throw new ServiceException(ErrorCode.READ_FILE_EXCEPTION);
        }
        return excelData;
    }

    public void downloadHostTemplate(HttpServletResponse response) throws IOException {
        ClassPathResource resource = new ClassPathResource("template/hosttemplate.xlsx");
        InputStream fis = new BufferedInputStream(resource.getInputStream());
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=template.xlsx");
        response.addHeader("Content-Length", "" + buffer.length);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        outputStream.write(buffer);
        outputStream.flush();
    }
}
