package com.ace.enums;

/**
 * Created by bamboo on 18-1-14.
 */
public enum WfeEnum {
    JUST_CREATED("待处理"),
    RUNNING("处理中"),
    COMPLETED("已整改"),
    RECALL("已撤销");

    private String value;

    public String getValue() {
        return value;
    }

    WfeEnum(String value) {
        this.value = value;
    }
}
