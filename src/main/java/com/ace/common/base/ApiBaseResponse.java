package com.ace.common.base;

import com.alibaba.fastjson.annotation.JSONField;

import groovy.transform.CompileStatic;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author bamboo
 */
@Data
@AllArgsConstructor
class ApiBaseResponse {

    @JSONField(name = "Code")
    @ApiModelProperty(required = true, notes = "JSON key: Code")
    String code;

    @JSONField(name = "Message")
    @ApiModelProperty(required = true, notes = "JSON key: Message")
    String message;

    @JSONField(name = "RequestId")
    @ApiModelProperty(required = true, notes = "JSON key: RequestId")
    String requestId;

    static ApiBaseResponse fromHttpStatus(HttpStatus httpStatus, String requestId) {
        return new ApiBaseResponse(
            httpStatus.toString(),
            httpStatus.getReasonPhrase(),
            requestId
        );
    }

    static ApiBaseResponse fromHttpStatus(HttpStatus httpStatus, String message, String requestId) {
        return new ApiBaseResponse(
            httpStatus.toString(),
            message != null ? message : httpStatus.getReasonPhrase(),
            requestId
        );
    }

    static ResponseEntity toResponseEntity(HttpStatus httpStatus, String requestId) {
        return new ResponseEntity(ApiBaseResponse.fromHttpStatus(httpStatus, requestId), httpStatus);
    }

}