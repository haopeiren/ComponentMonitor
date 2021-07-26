package com.ffcs.mapper;

import com.ffcs.model.Host;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haopeiren
 * @since 2021/7/23
 */
@Mapper
public interface HostMapper {
    /**
     * 保存host信息
     *
     * @param hostList host列表
     */
    int saveHost(@Param("list") List<Host> hostList);

    /**
     * 查询主机列表
     *
     * @param limit     分页大小
     * @param offset    偏移量
     * @param ip        ip
     * @param name      name
     * @return  host列表
     */
    List<Host> listHost(@Param("limit") Integer limit, @Param("offset") Integer offset,
                        @Param("ip") String ip, @Param("name") String name);
}
