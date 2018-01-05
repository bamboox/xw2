package com.ace.controller.news;

import com.ace.common.base.ApiBaseFileReqParam;
import lombok.Data;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiNewsReqParam {

    private String toDepartmentJson;

    private String title;
    private String context;

    private List<ApiBaseFileReqParam> files;

}
