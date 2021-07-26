package com.ffcs.model;

import lombok.Data;

/**
 * ComponentConnectInfo
 *
 * @author haopeiren
 * @since 2021/7/15
 */
@Data
public class ComponentConnectInfo {
    private Integer id;

    private Integer component;

    private Integer connectType;

    private String ip;

    private int port;

    private String database;

    private String username;

    private String password;

    private String tableName;

    private String columns;
}
