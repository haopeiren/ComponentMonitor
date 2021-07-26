package com.ffcs.mapper;

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
public interface MQMapper {
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

    /**
     * 查询所有MQ实例信息
     *
     * @return MQ实例信息列表
     */
    List<MQInstance> queryMQInstanceList(@Param("limit") Integer limit, @Param("offset") Integer offset);

    /**
     * 根据id查询MQ实例
     *
     * @param id 实例ID
     * @return  实例详情
     */
    MQInstance queryMQInstanceById(@Param("id") Integer id);
}
