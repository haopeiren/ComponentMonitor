package com.ffcs.service;

import com.ffcs.model.ComponentDBInfo;

import java.util.List;

/**
 * 组件信息同步service
 *
 * @author haopeiren
 * @since 2021/7/27
 */
public interface IComponentSyncService {
    /**
     * 同步组件信息
     *
     * @param dbInfoList 组件实例信息所在的数据库信息
     */
    void syncComponentInstances(List<ComponentDBInfo> dbInfoList);
}
