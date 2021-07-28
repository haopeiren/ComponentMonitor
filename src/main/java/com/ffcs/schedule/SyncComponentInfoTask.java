package com.ffcs.schedule;

import com.ffcs.constants.ComponentType;
import com.ffcs.mapper.ComponentDBInfoMapper;
import com.ffcs.model.ComponentDBInfo;
import com.ffcs.service.impl.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 同步组件信息
 *
 * @author haopeiren
 * @since 2021/7/15
 */
@Component
public class SyncComponentInfoTask {
    /**
     * MQService
     */
    @Autowired
    private MQService service;

    @Autowired
    private ComponentDBInfoMapper componentDBInfoMapper;

    /**
     * 启动时同步一次
     */
    @PostConstruct
    public void executeOnce() {
        sync();
    }

    /**
     * 定时同步MQ信息
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void syncMQInfo() {
        sync();
    }

    private void sync() {
        List<ComponentDBInfo> list =  componentDBInfoMapper.selectList(null);
        List<ComponentDBInfo> MQList = list
                .stream()
                .filter(e -> e.getComponentType() == ComponentType.MQ)
                .collect(Collectors.toList());
        service.syncComponentInstances(MQList);
    }
}
