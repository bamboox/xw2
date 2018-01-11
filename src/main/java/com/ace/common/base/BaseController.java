package com.ace.common.base;

import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import io.swagger.annotations.Api;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bamboo on 18-1-12.
 */
@Api
@RestController
public class BaseController {
    public String userId;
    public String departmentId;
    public String organizationId;
    public SysUser sysUser;
    public Department department;

    public void initBaseInfo() {
        sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userId = sysUser.getId();
        department = sysUser.getDepartment();
        departmentId = sysUser.getDepartment().getId();
        organizationId = department.getOrganization().getId();
    }
}

