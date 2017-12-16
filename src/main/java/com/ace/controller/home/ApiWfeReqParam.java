package com.ace.controller.home;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.ace.common.base.ApiBaseFileReqParam;
import lombok.Data;

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

    @NotNull
    private String message;

    private List<ApiBaseFileReqParam> files;

}
