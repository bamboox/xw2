package com.ace.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author bamboo
 */
@Data
@AllArgsConstructor
public class ApiBaseResponse {

    @JSONField(name = "Code")
    @ApiModelProperty(required = true, notes = "JSON key: Code")
    String code;

    @JSONField(name = "Message")
    @ApiModelProperty(required = true, notes = "JSON key: Message")
    String message;

    @JSONField(name = "RequestId")
    @ApiModelProperty(required = true, notes = "JSON key: RequestId")
    String requestId;

    @JSONField(name = "Data")
    @ApiModelProperty(required = true, notes = "JSON key: Data")
    Object data;

    public static ApiBaseResponse fromHttpStatus(HttpStatus httpStatus,Object data,String requestId) {
        return new ApiBaseResponse(
                httpStatus.toString(),
                httpStatus.getReasonPhrase(),
                requestId,
                data
        );
    }

    public static ApiBaseResponse fromHttpStatus(HttpStatus httpStatus, String message, String requestId) {
        return new ApiBaseResponse(
                httpStatus.toString(),
                message != null ? message : httpStatus.getReasonPhrase(),
                requestId,
                null
        );
    }

    public static ApiBaseResponse fromHttpStatus(HttpStatus httpStatus, Object data) {
        return new ApiBaseResponse(
                httpStatus.toString(),
                httpStatus.getReasonPhrase(),
                UUID.randomUUID().toString(),
                data
        );
    }
    public static ApiBaseResponse fromHttpStatus(HttpStatus httpStatus) {
        return new ApiBaseResponse(
                httpStatus.toString(),
                httpStatus.getReasonPhrase(),
                UUID.randomUUID().toString(),
                ""
        );
    }
    public static ResponseEntity toResponseEntity(HttpStatus httpStatus, String requestId) {
        return new ResponseEntity(ApiBaseResponse.fromHttpStatus(httpStatus, requestId), httpStatus);
    }

}