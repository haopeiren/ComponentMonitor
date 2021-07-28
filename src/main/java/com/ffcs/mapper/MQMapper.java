package com.ffcs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ffcs.model.MQInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MQMapper
 *
 * @author haopeiren
 * @since 2021/7/16
 */
@Mapper
public interface MQMapper extends BaseMapper<MQInstance> {
    /**
     * 保存MQ实例信息
     *
     * @param list MQ实例信息
     */
    void saveMQInstance(List<MQInstance> list);

    /**
     * 删除MQ实例信息(软删除)
     *
     * @param instanceIds MQ实例列表
     */
    void deleteMQInstance(List<Integer> instanceIds);
}
