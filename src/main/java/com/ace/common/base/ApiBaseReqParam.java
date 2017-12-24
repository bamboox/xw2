package com.ace.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.UUID;

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
    @Valid
    private T bizParams;
}
