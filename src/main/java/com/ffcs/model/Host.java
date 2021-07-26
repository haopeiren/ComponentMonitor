package com.ffcs.model;

import lombok.Data;

/**
 * Host
 *
 * @author haopeiren
 * @since 2021/7/23
 */
@Data
public class Host {
    private Integer id;

    private String ip;

    private Integer type;

    private String name;
}
