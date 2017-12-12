package com.ace.controller.auth;

import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.DepartmentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bamboo on 17-12-5.
 */
@Api(value = "部门controller", description = "部门操作")
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all Department.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Department> getDiscoveryAll(@PageableDefault(value = 20, sort = { "gmtCreated" }, direction = Sort.Direction.DESC)
                                                   Pageable pageable,@RequestParam(value = "name",defaultValue = "")String name) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String oId = sysUser.getDepartment().getOrganization().getId();
        String id = sysUser.getDepartment().getId();

        return departmentRepository.findAllByOrganization_IdAndNameContainingAndIdNot(oId,name,id,pageable);
    }
}
