package com.ace.controller.evaluate;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.EvaluateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bamboo on 18-1-8.
 */
//@Api
@Api
@RestController
@RequestMapping("/api/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateRepository evaluateRepository;
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single evaluate.", notes = "You have to provide a valid evaluate ID.")
    @ResponseBody
    public ResponseEntity<?> getEvaluate() throws Exception {

        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();

        return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, evaluateRepository.findByOrganizationId(organizationId)));
    }
}
