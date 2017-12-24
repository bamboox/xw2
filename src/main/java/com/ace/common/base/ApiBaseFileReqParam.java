package com.ace.common.base;

import lombok.Data;

/**
 * @author bamboo
 */
@Data
public class ApiBaseFileReqParam {
    private long size;
    private String type;
    private String name;
    private String base64;
}
