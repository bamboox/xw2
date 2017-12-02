package com.ace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by bamboo on 17-12-1.
 */
@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String code;
    private String message;

    public ApiErrorResponse(String code, String message) {
        this.status = HttpStatus.NOT_FOUND.value();
        this.code = code;
        this.message = message;
    }
}
