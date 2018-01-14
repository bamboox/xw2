package com.ace.enums;

/**
 * Created by bamboo on 18-1-14.
 */
public enum OperateEnum {
    PASS("通过"),
    REFUSE("拒绝"),
    RECALL("撤回"),
    DOING("处理"),
    REMINDER("催办"),
    SELECT("移交");

    private String value;

    public String getValue() {
        return value;
    }

    OperateEnum(String value) {
        this.value = value;
    }
}
