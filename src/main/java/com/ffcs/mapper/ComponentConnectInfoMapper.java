package com.ffcs.mapper;

import com.ffcs.model.ComponentConnectInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author haopeiren
 * @since 2021/7/15
 */
@Mapper
public interface ComponentConnectInfoMapper {
    /**
     * 获取指定组件的连接信息
     *
     * @param component 组件
     * @return 连接信息
     */
    ComponentConnectInfo getConnectionInfo(Integer component);
}
