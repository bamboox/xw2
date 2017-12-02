package com.ace.controller.auth;

import com.ace.common.base.ApiBaseReqParam;
import com.ace.entity.SysUser;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by bamboo on 17-12-2.
 */
@Data
public class ApiAuthReqParam extends ApiBaseReqParam {
    @JSONField(name = "bizParams")
    private SysUser sysUser;
}
