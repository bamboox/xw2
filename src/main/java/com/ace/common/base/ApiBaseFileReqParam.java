package com.ace.common.base;

import java.util.UUID;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

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
