package com.ffcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ffcs.dto.HostListDto;
import com.ffcs.model.Host;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * HostService
 *
 * @author haopeiren
 * @since 2021/7/23
 */
@Service
@Slf4j
public class HostService extends ServiceImpl<BaseMapper<Host>, Host> {

    /**
     * 查询主机列表
     *
     * @param page     当前页
     * @param size    每页大小
     * @param ip        ip
     * @return  查询结果
     */
    public IPage<Host> listHost(Integer page, Integer size, String ip) {
        IPage<Host> hostPage = new Page<>();
        hostPage.setCurrent(page);
        hostPage.setSize(size);

        LambdaQueryWrapper<Host> wrapper = new QueryWrapper<Host>().lambda();
        if (StringUtils.isNotBlank(ip)) {
            wrapper.like(Host::getIp, ip);
        }
        hostPage = getBaseMapper().selectPage(hostPage, wrapper);
        return hostPage;
    }

    /**
     * 导入host列表
     *
     * @param hosts host列表
     */
    public void importHosts(HostListDto hosts) {
        List<Host> hostList = hosts.getHosts().stream()
                .map(ip -> {
                    Host host = new Host();
                    host.setIp(ip);
                    return host;
                })
                .collect(Collectors.toList());
        saveBatch(hostList);
    }
}
