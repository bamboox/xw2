package com.ace.controller.home;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.Department;
import com.ace.entity.Discovery;
import com.ace.entity.SysUser;
import com.ace.repository.DiscoveryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

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

        return ResponseEntity.created(URI.create(request.getRequestURL().append("/").append(discovery.getId()).toString())).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, apiDiscoveryReqParam.getRequestId()));

    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all Discovery.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20)")
    @ResponseBody
    public Page<Discovery> getDiscovery(@ApiParam(value = "The page number (zero-based)", required = true)
                                        @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                        @ApiParam(value = "Tha page size", required = true)
                                        @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        Pageable pageable = new PageRequest(page, size);
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();

        return discoveryRepository.findAllById(userId, pageable);
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
    @ApiOperation(value = "Update a SysUser resource.", notes = "You have to provide a valid SysUser ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateDiscovery(@ApiParam(value = "The ID of the existing SysUser resource.", required = true)
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
