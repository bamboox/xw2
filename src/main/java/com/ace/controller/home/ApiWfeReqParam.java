package com.ace.controller.home;

import com.ace.common.base.ApiBaseFileReqParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiWfeReqParam {
    @NotNull
    private String wfeId;
    @NotNull
    private String taskId;
    @NotNull
    private String operate;

    private String departmentId;

    @NotNull
    private String message;

    private List<ApiBaseFileReqParam> files;

}
