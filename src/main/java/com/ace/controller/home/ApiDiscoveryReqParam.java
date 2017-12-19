package com.ace.controller.home;

import com.ace.common.base.ApiBaseFileReqParam;
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
    private String location;
    @NotBlank
    private String description;
    @NotBlank
    private String sendDepartmentId;
    private List<ApiBaseFileReqParam> files;

}
