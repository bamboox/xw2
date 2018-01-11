package com.ace.controller.home;

import com.ace.common.base.ApiBaseFileReqParam;
import com.ace.common.base.ApiBaseReqParam;
import com.ace.common.base.ApiBaseResponse;
import com.ace.common.exception.DataFormatException;
import com.ace.entity.Discovery;
import com.ace.entity.Task;
import com.ace.entity.Wfe;
import com.ace.entity.file.Image;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.DepartmentRepository;
import com.ace.repository.DiscoveryRepository;
import com.ace.repository.TaskRepository;
import com.ace.repository.WfeRepository;
import com.ace.repository.wfe.IProcessInstanceService;
import com.ace.service.AsyncTaskService;
import com.ace.service.MsgService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bamboo on 17-12-2.
 */
@Api(value = "首页controller", description = "首页操作")
@RestController
@RequestMapping("/api/discovery")
//@Log
//@Validated
public class DiscoveryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${web.upload-path}")
    private String webUploadPath;
    private final ResourceLoader resourceLoader;
    private DiscoveryRepository discoveryRepository;
    private IProcessInstanceService iProcessInstanceService;
    private TaskRepository taskRepository;
    private WfeRepository wfeRepository;
    private DepartmentRepository departmentRepository;
    private AsyncTaskService asyncTaskService;
    private MsgService msgService;

    @Autowired
    public DiscoveryController(ResourceLoader resourceLoader, DiscoveryRepository discoveryRepository,
                               IProcessInstanceService iProcessInstanceService, TaskRepository taskRepository,
                               WfeRepository wfeRepository, DepartmentRepository departmentRepository,
                               AsyncTaskService asyncTaskService, MsgService msgService) {
        this.resourceLoader = resourceLoader;
        this.discoveryRepository = discoveryRepository;
        this.iProcessInstanceService = iProcessInstanceService;
        this.taskRepository = taskRepository;
        this.wfeRepository = wfeRepository;
        this.departmentRepository = departmentRepository;
        this.asyncTaskService = asyncTaskService;
        this.msgService = msgService;

    }

    /*@RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a Discovery resource.", notes = "Returns the URL of the new resource in the
    Location header.")
    public ResponseEntity<?> createDiscovery(@RequestBody ApiDiscoveryReqParam apiDiscoveryReqParam,
    HttpServletRequest request) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Discovery discovery = apiDiscoveryReqParam.getDiscovery();

        discovery.setUserId(userId);
        discovery.setDepartmentId(departmentId);

        discoveryRepository.save(discovery);

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator).concat(discovery
        .getId()).toString())).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, discovery,
        apiDiscoveryReqParam.getRequestId()));

    }*/

    @PostMapping(value = "submit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a Discovery resource.",
            notes = "Returns the URL of the new resource in the Location header.")
    public ResponseEntity<?> submit(@RequestBody @Valid ApiBaseReqParam<ApiDiscoveryReqParam> apiBaseReqParam,
                                    HttpServletRequest request) {

        ApiDiscoveryReqParam bizParams = apiBaseReqParam.getBizParams();
        List<ApiBaseFileReqParam> files = bizParams.getFiles();

        logger.debug("files:" + files.size());

        if (files.size() > 6) {
            throw new DataFormatException("files length max 6");
        }

        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();

        Discovery discovery = new Discovery();
        discovery.setLatitude(bizParams.getLatitude());
        discovery.setLongitude(bizParams.getLongitude());
        discovery.setOrganizationId(organizationId);
        discovery.setLocation(bizParams.getLocation());
        discovery.setDescription(bizParams.getDescription());
        discovery.setUserId(userId);
        discovery.setDepartmentId(departmentId);

        //

        String keyPrefix = organizationId + "_" + departmentId + "_" + userId + "_" + String.valueOf(
                System.currentTimeMillis());
        Set<Image> imageSet = asyncTaskService.save2Qiniu(files, keyPrefix, userId);
        asyncTaskService.uploadQiniu(keyPrefix, files);
        discovery.setImageSet(imageSet);
//        discoveryRepository.save(discovery);

        Wfe wfe = new Wfe();
        wfe.setDiscovery(discovery);
        wfe.setCreateUserId(userId);
        wfe.setCreateDepartmentId(departmentId);
        wfe.setOrganizationId(organizationId);

        wfe.setState("JUST_CREATED");

        Task createTask = new Task();
        createTask.setFromDepartmentId(departmentId);
        createTask.setFromDepartmentName(department.getName());
        createTask.setFromUserId(userId);
        createTask.setFromUserName(sysUser.getName());

        createTask.setToDepartmentId(departmentId);
        createTask.setToDepartmentName(department.getName());
        createTask.setToUserId(userId);
        createTask.setToUserName(sysUser.getName());

        createTask.setNodeType("START");
        createTask.setState("START");
        createTask.setNextOperate("recall");
        createTask.setWfe(wfe);
        createTask.setOrderNo(0);

        Task task = new Task();
        task.setFromDepartmentId(departmentId);
        task.setFromDepartmentName(department.getName());
        task.setFromUserId(userId);
        task.setFromUserName(sysUser.getName());


        task.setNodeType("TASK_NODE");
        task.setState("UNSTATE");
        //if wenmingbang

        String typeCode = department.getTypeCode();
        if ("00000".equals(typeCode)) {//

            task.setNextOperate("doing");
            task.setToDepartmentId(bizParams.getSendDepartmentId());
            task.setToDepartmentName(departmentRepository.findOne(bizParams.getSendDepartmentId()).getName());

            wfe.setToDepartmentId(bizParams.getSendDepartmentId());
        } else {
            Department fastenDepartment = departmentRepository.findByOrganization_IdAndTypeCode(organizationId, "00000");

            task.setNextOperate("select");
            task.setToDepartmentId(fastenDepartment.getId());
            task.setToDepartmentName(fastenDepartment.getName());

            wfe.setToDepartmentId(fastenDepartment.getId());
        }
        task.setWfe(wfe);
        task.setOrderNo(1);

        Set<Task> taskSet = new HashSet<>();

        taskSet.add(createTask);
        taskSet.add(task);

        wfe.setTaskSet(taskSet);
        wfeRepository.save(wfe);

        String context = department.getName() + "部门发起反馈!";
        msgService.sendMsgByTag(context, "您有新任务来了!", ImmutableMap.of("id", wfe.getId(), "activity", "wfe"),
                bizParams.getSendDepartmentId());

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator)
                .concat(discovery.getId()).toString())).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, discovery));
    }


    /*@RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all Discovery.", notes = "The list is paginated. You can provide a
     page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Discovery> getDiscoveryAll(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort
    .Direction.DESC)
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
    @ApiOperation(value = "Update a SysUser resource.", notes = "You have to provide a valid Discovery ID in the URL
    and in the payload. The ID attribute can not be updated.")
    public void updateDiscovery(@ApiParam(value = "The ID of the existing Discovery resource.", required = true)
                                @PathVariable("id") String id, @RequestBody ApiDiscoveryReqParam apiDiscoveryReqParam) {
        Discovery discovery = apiDiscoveryReqParam.getDiscovery();

        this.discoveryRepository.save(discovery);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a Discovery resource.", notes = "You have to provide a valid Discovery ID in the
    URL. Once deleted the resource can not be recovered.")
    public void deleteDiscovery(@ApiParam(value = "The ID of the existing SysUser resource.", required = true)
                                @PathVariable("id") String id) {

        this.discoveryRepository.delete(id);
    }*/
}
