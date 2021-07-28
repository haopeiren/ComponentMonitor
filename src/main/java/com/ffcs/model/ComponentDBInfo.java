package com.ffcs.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * ComponentConnectInfo
 *
 * @author haopeiren
 * @since 2021/7/15
 */
@Data
@TableName("components")
public class ComponentDBInfo {
    private Integer id;

    private Integer componentType;

    private String ip;

    private Integer port;

    private String userName;

    private String userPass;

    private String dbName;

    private int envType;
}
