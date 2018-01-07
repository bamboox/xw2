package com.ace.controller.home;

import com.ace.common.base.ApiBaseFileReqParam;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiDiscoveryReqParam {
    private double latitude;
    private double longitude;
    @NotBlank
    @ApiParam(required = true)
    private String location;
    @NotBlank
    @ApiParam(required = true)
    private String description;
//    @NotBlank
//    @ApiParam(required = true)
    private String sendDepartmentId;
    private List<ApiBaseFileReqParam> files;

}
