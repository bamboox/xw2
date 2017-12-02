package com.ace.controller;

import com.ace.common.exception.ResourceNotFoundException;
import com.ace.dao.SysUserRepository;
import com.ace.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by sang on 2017/1/10.
 */
//@ApiIgnore
@Api
@RestController
@RequestMapping("/home")
public class HomeController {
    protected static final String DEFAULT_PAGE_SIZE = "100";
    protected static final String DEFAULT_PAGE_NUM = "0";


    @Autowired
    private SysUserRepository userRepository;

    @RequestMapping(value = "/x",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
//    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a SysUser resource.", notes = "Returns the URL of the new resource in the Location header.")
    public ResponseEntity<?> createSysUser(@RequestBody SysUser SysUser,
                              HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader("Location", request.getRequestURL().append("/").append("1").toString());
        URI location = null;
        try {
            location = new URI("http://www.concretepage.com/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Hello World", new HttpHeaders(), HttpStatus.CREATED);


//        throw  new ResourceNotFoundException();
    }

    /*@RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all SysUsers.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<SysUser> getAllSysUser(@ApiParam(value = "The page number (zero-based)", required = true)
                                @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                @ApiParam(value = "Tha page size", required = true)
                                @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                HttpServletRequest request, HttpServletResponse response) {
        Pageable pageable = new PageRequest(page, size);
        return userRepository.findAll(pageable);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single SysUser.", notes = "You have to provide a valid SysUser ID.")
    public
    @ResponseBody
    SysUser getSysUser(@ApiParam(value = "The ID of the SysUser.", required = true)
                       @PathVariable("id") Long id,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser SysUser = userRepository.findOne(id);
        //todo: http://goo.gl/6iNAkz
        return SysUser;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a SysUser resource.", notes = "You have to provide a valid SysUser ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateSysUser(@ApiParam(value = "The ID of the existing SysUser resource.", required = true)
                              @PathVariable("id") Long id, @RequestBody SysUser sysUser,
                              HttpServletRequest request, HttpServletResponse response) {

        this.userRepository.save(sysUser);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a SysUser resource.", notes = "You have to provide a valid SysUser ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteSysUser(@ApiParam(value = "The ID of the existing SysUser resource.", required = true)
                              @PathVariable("id") Long id, HttpServletRequest request,
                              HttpServletResponse response) {

        this.userRepository.delete(id);
    }*/
}
