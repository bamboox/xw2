package com.ace.controller.home;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.file.Image;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.entity.workflow.Discovery;
import com.ace.repository.DiscoveryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-12-2.
 */
@Api(value = "首页controller", description = "首页操作")
@RestController
@RequestMapping("/api/discovery")
public class DiscoveryController {
    @Value("${web.upload-path}")
    private String webUploadPath;
    private final ResourceLoader resourceLoader;
    private DiscoveryRepository discoveryRepository;

    @Autowired
    public DiscoveryController(ResourceLoader resourceLoader, DiscoveryRepository discoveryRepository) {
        this.resourceLoader = resourceLoader;
        this.discoveryRepository = discoveryRepository;
    }

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

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator).concat(discovery.getId()).toString())).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, discovery, apiDiscoveryReqParam.getRequestId()));

    }


    @RequestMapping(value = "submit",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a Discovery resource.", notes = "Returns the URL of the new resource in the Location header.")
    public ResponseEntity<?> submit(@RequestParam("file") MultipartFile files[],
                                    @RequestParam Long latitude,
                                    @RequestParam Long longitude,
                                    @RequestParam String location,
                                    @RequestParam String description,
                                    @RequestParam String sendDepartmentId,
                                    HttpServletRequest request) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();


        Discovery discovery=new Discovery();
        discovery.setLatitude(latitude);
        discovery.setLongitude(longitude);
        discovery.setLocation(location);
        discovery.setDescription(description);
        discovery.setUserId(userId);
        discovery.setDepartmentId(departmentId);

        //

        Set<Image> imageSet = new HashSet<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                if (file.getContentType().contains("image")) {
                    try {
                        String fileName = file.getOriginalFilename();
                        String extensionName = StringUtils.substringAfter(fileName, ".");
                        String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                        String datdDirectory = organizationId.concat(File.separator).concat(departmentId).concat(File.separator).concat(userId).concat(File.separator);
                        String filePath = webUploadPath.concat(datdDirectory);
                        File dest = new File(filePath, newFileName);
                        if (!dest.getParentFile().exists()) {
                            dest.getParentFile().mkdirs();
                        }

                        file.transferTo(dest);
                        String imageUrl = "/api/image/".concat(datdDirectory).concat(newFileName);
                        Image image = new Image();
                        image.setUrl(sysUser.getId());
                        image.setUrl(imageUrl);
                        image.setUserId(userId);
                        imageSet.add(image);
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body("");
                    }
                }
            }
        }
        discovery.setImageSet(imageSet);

        discoveryRepository.save(discovery);

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator).concat(discovery.getId()).toString())).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, discovery));

    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all Discovery.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Discovery> getDiscoveryAll(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                                   Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();

        return discoveryRepository.findAllByUserId(userId, pageable);
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
