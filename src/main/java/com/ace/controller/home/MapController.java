package com.ace.controller.home;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.DiscoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bamboo on 17-12-8.
 */
@RestController
@RequestMapping("/api/map")
public class MapController {

    private DiscoveryRepository discoveryRepository;

    @Autowired
    public MapController(DiscoveryRepository discoveryRepository) {
        this.discoveryRepository = discoveryRepository;
    }

    @RequestMapping(value = "data",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getMapData() {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();


        return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, discoveryRepository.findByOrganizationId(organizationId)));
    }
}
