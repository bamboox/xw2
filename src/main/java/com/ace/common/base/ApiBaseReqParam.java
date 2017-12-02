package com.ace.common.base;

import java.util.UUID;

import org.springframework.util.StringUtils;

/**
 * @author bamboo
 */
public class ApiBaseReqParam {
    String requestId;

    String getRequestId() {
        if (StringUtils.isEmpty(this.requestId)) {
            this.requestId = UUID.randomUUID().toString();
        }
        return this.requestId;
    }
}
