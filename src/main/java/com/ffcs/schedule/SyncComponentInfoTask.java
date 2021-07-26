package com.ffcs.schedule;

import com.ffcs.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

    /**
     * 启动时同步一次
     */
    @PostConstruct
    public void executeOnce() {
        service.syncMQInstanceInfo();
    }

    /**
     * 定时同步MQ信息
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void syncMQInfo() {
        service.syncMQInstanceInfo();
    }
}
