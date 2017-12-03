package com.ace.controller.home;

import com.ace.common.base.ApiBaseReqParam;
import com.ace.entity.Discovery;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiDiscoveryReqParam extends ApiBaseReqParam {
    @JSONField(name = "bizParams")
    @ApiModelProperty(value = "bizParams")
    private Discovery discovery;
}
