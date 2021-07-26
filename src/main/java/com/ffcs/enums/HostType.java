package com.ffcs.enums;

/**
 * 主机类型
 *
 * @author haopeiren
 * @since 2021/7/23
 */
public enum HostType {
    PHYSICAL(1, "物理机"),
    VIRTUAL(2, "虚拟机");
    HostType(int value, String name) {
        this.name = name;
        this.value = value;
    }
    private int value;

    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
