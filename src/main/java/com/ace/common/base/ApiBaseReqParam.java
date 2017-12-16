package com.ace.common.base;

import java.util.HashMap;
import java.util.UUID;

import com.alibaba.fastjson.annotation.JSONField;

import com.ace.entity.Discovery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author bamboo
 */
@Data
public class ApiBaseReqParam<T> {
    private String requestId;

    public String getRequestId() {
        if (StringUtils.isEmpty(this.requestId)) {
            this.requestId = UUID.randomUUID().toString();
        }
        return this.requestId;
    }

    @JSONField(name = "bizParams")
    @ApiModelProperty(value = "bizParams")
    private T bizParams;
}
