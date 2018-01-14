package com.ace.enums;

/**
 * Created by bamboo on 18-1-14.
 */
public enum NodeEnum {
    START("等待审批"),
    TASK_NODE("发起反馈"),
    END("等待处理");

    private String value;

    public String getValue() {
        return value;
    }

    NodeEnum(String value) {
        this.value = value;
    }
}
