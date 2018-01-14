package com.ace.enums;

/**
 * Created by bamboo on 18-1-14.
 */
public enum TestEnum {

    JUST_CREATED("待处理");

    private String typeName;


    private TestEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
