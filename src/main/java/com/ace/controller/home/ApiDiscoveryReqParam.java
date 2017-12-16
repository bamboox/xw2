package com.ace.controller.home;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.ace.common.base.ApiBaseFileReqParam;
import com.ace.common.base.ApiBaseReqParam;
import com.ace.entity.Discovery;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiDiscoveryReqParam {

    private double latitude;
    private double longitude;
    private String location;
    private String description;
    @NotNull
    private String sendDepartmentId;
    private List<ApiBaseFileReqParam> files;

}
