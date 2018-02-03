package com.ace.enums;

/**
 * Created by bamboo on 18-1-14.
 */
public enum TaskEnum {
    START("发起反馈"),
    UN_STATE("等待处理"),
    WAIT("等待审批"),
    PASS("审批通过"),
    REFUSE("审批拒绝"),
    REFUSE_1("拒绝处理"),
    COMPLETED("结束"),
    SELECTED("移交"),
    DONE("已整改"),
    RECALL("已整改");

    private String value;

    public String getValue() {
        return value;
    }

    TaskEnum(String value) {
        this.value = value;
    }
}
