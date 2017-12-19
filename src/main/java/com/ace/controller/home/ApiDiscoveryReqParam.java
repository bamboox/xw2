package com.ace.controller.home;

import com.ace.common.base.ApiBaseFileReqParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiDiscoveryReqParam {
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private String location;
    @NotNull
    private String description;
    @NotNull
    private String sendDepartmentId;
    private List<ApiBaseFileReqParam> files;

}
