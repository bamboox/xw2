package com.ace.controller.home;

import com.ace.common.base.ApiBaseFileReqParam;
import com.ace.common.base.ApiBaseReqParam;
import com.ace.common.base.ApiBaseResponse;
import com.ace.common.exception.DataFormatException;
import com.ace.entity.Task;
import com.ace.entity.Wfe;
import com.ace.entity.file.Image;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.DepartmentRepository;
import com.ace.repository.SysUserRepository;
import com.ace.repository.TaskRepository;
import com.ace.repository.WfeRepository;
import com.ace.service.AsyncTaskService;
import com.ace.service.MsgService;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Created by bamboo on 17-12-8.
 */
@RestController
@RequestMapping("/api/wfe")
public class WfeController {
    private TaskRepository taskRepository;
    private WfeRepository wfeRepository;
    @Value("${web.upload-path}")
    private String webUploadPath;
    private final ResourceLoader resourceLoader;
    private AsyncTaskService asyncTaskService;
    private MsgService msgService;
    private SysUserRepository sysUserRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public WfeController(TaskRepository taskRepository, WfeRepository wfeRepository, ResourceLoader resourceLoader,
                         AsyncTaskService asyncTaskService, MsgService msgService,
                         SysUserRepository sysUserRepository,
                         DepartmentRepository departmentRepository) {
        this.taskRepository = taskRepository;
        this.wfeRepository = wfeRepository;
        this.resourceLoader = resourceLoader;
        this.asyncTaskService = asyncTaskService;
        this.msgService = msgService;
        this.sysUserRepository = sysUserRepository;
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping(value = "wait",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.",
            notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more "
                    + "?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getWaitWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                        Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdIsNull(departmentId,
                pageable);
        wfes.getContent().forEach(wfe -> {
            process(wfe, userId);
        });
        return wfes;
    }

    @RequestMapping(value = "done",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.",
            notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more "
                    + "?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getDoneWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                        Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserId(departmentId, userId,
                pageable);
        return wfes;
    }

    @RequestMapping(value = "create",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.",
            notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more "
                    + "?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getCreateWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                          Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByCreateUserId(userId, pageable);
        wfes.getContent().forEach(wfe -> {
            processCreate(wfe, userId);
        });
        return wfes;
    }

    @RequestMapping(value = "about",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.",
            notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more "
                    + "?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getAboutWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByStateNotAndTaskSet_ToDepartmentIdOrTaskSet_FromDepartmentId("recall", departmentId,
                departmentId, pageable);
        return wfes;
    }

    @PostMapping(value = "operate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "operate :doing pass refuse",
            notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more "
                    + "?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public ResponseEntity<?> operateWfe(@Valid @RequestBody ApiBaseReqParam<ApiWfeReqParam> apiBaseReqParam,
                                        HttpServletRequest request) {
        ApiWfeReqParam bizParams = apiBaseReqParam.getBizParams();
        String taskId = bizParams.getTaskId();
        String wfeId = bizParams.getWfeId();
        String operate = bizParams.getOperate();
        String message = bizParams.getMessage();
        List<ApiBaseFileReqParam> files = bizParams.getFiles();
        if (files.size() > 6) {
            throw new DataFormatException("files length max 6");
        }
        //operate pass
        //operate refuse
        //operate recall
        //operate doing
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String fromDepartment = department.getName();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();

        Task task = taskRepository.findOne(taskId);
        Wfe wfe = wfeRepository.findOne(wfeId);

        SysUser fromUser = sysUserRepository.findOne(task.getFromUserId());

        if ("select".equals(operate)) {

            //
            String selectDepartmentId = bizParams.getDepartmentId();
            Department selectDepartment = departmentRepository.getOne(selectDepartmentId);

            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("WAIT");
            task.setMessage(message);

            taskRepository.save(task);

            Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());

            createTask.setToDepartmentId(selectDepartment.getId());
            createTask.setToDepartmentName(selectDepartment.getName());

            createTask.setNodeType("TASK_NODE");
            createTask.setOrderNo(task.getOrderNo() + 1);
            createTask.setState("UNSTATE");
            createTask.setNextOperate("doing");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);
            wfe.setState("RUNNING");
            wfeRepository.save(wfe);

            String context = fromDepartment + "发起反馈!";
            msgService.sendMsgByTag(context, "您有新任务来了!", ImmutableMap.of("id", wfe.getId(), "activity", "wfe"),
                    task.getFromDepartmentId());


        } else if ("doing".equals(operate)) {
            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("WAIT");
            task.setMessage(message);

            String keyPrefix = organizationId + "_" + departmentId + "_" + userId + "_" + String.valueOf(
                    System.currentTimeMillis());

            Set<Image> imageSet = asyncTaskService.save2Qiniu(files, keyPrefix, userId);
            asyncTaskService.uploadQiniu(keyPrefix, files);
            task.setImageSet(imageSet);

            taskRepository.save(task);

            Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());

            createTask.setToDepartmentId(task.getFromDepartmentId());
            createTask.setToDepartmentName(task.getFromDepartmentName());

            createTask.setNodeType("TASK_NODE");
            createTask.setOrderNo(task.getOrderNo() + 1);
            createTask.setState("UNSTATE");
            createTask.setNextOperate("pass;refuse");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);
            wfe.setState("RUNNING");
            wfeRepository.save(wfe);

            String context = fromDepartment + "发起反馈!";
            msgService.sendMsgByTag(context, "您有新任务来了!", ImmutableMap.of("id", wfe.getId(), "activity", "wfe"),
                    task.getFromDepartmentId());

        } else if ("pass".equals(operate)) {
            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("PASS");
            task.setMessage(message);
            taskRepository.save(task);

            /*Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());

            createTask.setNodeType("END");
            createTask.setOrderNo(task.getOrderNo() + 1);
            createTask.setState("COMPLETED");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);*/

            wfe.setState("COMPLETED");
            wfeRepository.save(wfe);

            String context = fromDepartment + "发起反馈!";
            msgService.sendMsgByTag(context, "您有新任务来了!", ImmutableMap.of("id", wfe.getId(), "activity", "wfe"),
                    task.getFromDepartmentId());

        } else if ("refuse".equals(operate)) {
            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("REFUSE");
            task.setMessage(message);
            taskRepository.save(task);

            Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());

            createTask.setToDepartmentId(task.getFromDepartmentId());
            createTask.setToDepartmentName(task.getFromDepartmentName());

            createTask.setNodeType("TASK_NODE");
            createTask.setState("UNSTATE");
            createTask.setOrderNo(task.getOrderNo() + 1);
            createTask.setNextOperate("doing");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);

            String context = fromDepartment + "发起反馈!";
            msgService.sendMsgByAlias(context, "您有新任务来了!", ImmutableMap.of("id", wfe.getId()), task.getFromUserId(), "activity", "wfe");

        } else if ("reminder".equals(operate)) {
            String context = fromDepartment + "发来提醒!";
            msgService.sendMsgByAlias(context, "您有新任务来了!", ImmutableMap.of("id", wfe.getId()), task.getFromUserId(), "activity", "wfe");
        } else if ("recall".equals(operate)) {


            /*task.setToUserId(userId);
            task.setToUserName(sysUser.getName());*/
            task.setState("recall");
            task.setMessage(message);
            taskRepository.save(task);


            wfe.setState("RECALL");
            wfeRepository.save(wfe);
        } else {
            throw new DataFormatException("operate not exits");
        }

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator))).body(
                ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single Wfe.", notes = "You have to provide a valid Wfe ID.")
    @ResponseBody
    public Wfe getWfe(@ApiParam(value = "The ID of the Wfe.", required = true)
                      @PathVariable("id") String id
    ) throws Exception {

        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Wfe wfe = wfeRepository.findOne(id);

        return process(wfe, userId);
    }

    private Wfe process(Wfe wfe, String userId) {
        /*wfe.getTaskSet().forEach(task -> {
                    if (task.getFromUserId().equals(userId)) {
                        wfe.setCurrentTask(task);
                    }
                }
        );
        if (wfe.getCurrentTask() == null) {
            Task[] tasks = wfe.getTaskSet().toArray(new Task[wfe.getTaskSet().size()]);
            wfe.setCurrentTask(tasks[tasks.length - 1]);
        }*/
        Task[] tasks = wfe.getTaskSet().toArray(new Task[wfe.getTaskSet().size()]);
        Task lastTask = tasks[tasks.length - 1];
        wfe.setCurrentTask(lastTask);
        return wfe;
    }

    private Wfe processCreate(Wfe wfe, String userId) {
        /*wfe.getTaskSet().forEach(task -> {
                    if (task.getFromUserId().equals(userId)) {
                        wfe.setCurrentTask(task);
                    }
                }
        );*/
        Task[] tasks = wfe.getTaskSet().toArray(new Task[wfe.getTaskSet().size()]);
        Task lastTask = tasks[tasks.length - 1];
        Task task = taskRepository.findByWfe_IdAndOrderNo(wfe.getId(), 1);
        if (Strings.isNullOrEmpty(task.getToUserId())) {
            lastTask.setNextOperate("recall");
            wfe.setCurrentTask(lastTask);
        } else if (tasks.length > 2) {
            lastTask.setNextOperate("");
            wfe.setCurrentTask(lastTask);
        } else {
            lastTask.setNextOperate("reminder");
            wfe.setCurrentTask(lastTask);
        }
        return wfe;
    }
}
