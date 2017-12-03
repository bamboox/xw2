package com.ace.controller.home;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.Discovery;
import com.ace.entity.SysUser;
import com.ace.repository.DiscoveryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;

/**
 * Created by bamboo on 17-12-2.
 */
@Api(value = "首页controller", description = "首页操作")
@RestController
@RequestMapping("/api/discovery")
public class DiscoveryController {
    protected static final String DEFAULT_PAGE_SIZE = "20";
    protected static final String DEFAULT_PAGE_NUM = "0";
    @Autowired
    private DiscoveryRepository discoveryRepository;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a Discovery resource.", notes = "Returns the URL of the new resource in the Location header.")
    public ResponseEntity<?> createDiscovery(@RequestBody ApiDiscoveryReqParam apiDiscoveryReqParam, HttpServletRequest request) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Discovery discovery = apiDiscoveryReqParam.getDiscovery();

        discovery.setUserId(userId);
        discovery.setDepartmentId(departmentId);

        discoveryRepository.save(discovery);

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator).concat(discovery.getId()).toString())).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, discovery,apiDiscoveryReqParam.getRequestId()));

    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all Discovery.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Discovery> getDiscoveryAll(@PageableDefault(value = 20, sort = { "gmtCreated" }, direction = Sort.Direction.DESC)
                                                       Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();

        return discoveryRepository.findAllByUserId(userId,pageable);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single SysUser.", notes = "You have to provide a valid Discovery ID.")
    @ResponseBody
    public Discovery getDiscovery(@ApiParam(value = "The ID of the Discovery.", required = true)
                                  @PathVariable("id") String id
    ) throws Exception {

        Discovery discovery = discoveryRepository.findOne(id);
        return discovery;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a SysUser resource.", notes = "You have to provide a valid Discovery ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateDiscovery(@ApiParam(value = "The ID of the existing Discovery resource.", required = true)
                                @PathVariable("id") String id, @RequestBody ApiDiscoveryReqParam apiDiscoveryReqParam) {
        Discovery discovery = apiDiscoveryReqParam.getDiscovery();

        this.discoveryRepository.save(discovery);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a Discovery resource.", notes = "You have to provide a valid Discovery ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteDiscovery(@ApiParam(value = "The ID of the existing SysUser resource.", required = true)
                                @PathVariable("id") String id) {

        this.discoveryRepository.delete(id);
    }
}
