package com.ffcs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author haopeiren
 * @since 2021/7/27
 */

@Data
@EqualsAndHashCode(of = "id")
public class BaseInstance {
    private Integer id;

    private int del;

    private Timestamp syncTime;

    private int envType;
}
