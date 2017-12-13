package com.ace.controller.home;

import com.ace.common.base.ApiBaseResponse;
import com.ace.common.exception.DataFormatException;
import com.ace.entity.Task;
import com.ace.entity.Wfe;
import com.ace.entity.file.Image;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.TaskRepository;
import com.ace.repository.WfeRepository;
import com.ace.service.AsyncTaskService;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;
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
    @Autowired
    public WfeController(TaskRepository taskRepository, WfeRepository wfeRepository, ResourceLoader resourceLoader,AsyncTaskService asyncTaskService) {
        this.taskRepository = taskRepository;
        this.wfeRepository = wfeRepository;
        this.resourceLoader = resourceLoader;
        this.asyncTaskService = asyncTaskService;
    }

    @RequestMapping(value = "wait",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getWaitWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                        Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdIsNull(departmentId, pageable);

        return wfes;
    }

    @RequestMapping(value = "done",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getDoneWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                        Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserId(departmentId, userId, pageable);
        return wfes;
    }

    @RequestMapping(value = "create",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getCreateWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                          Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdAndTaskSet_NodeType(departmentId, userId, "START", pageable);
        return wfes;
    }

    @RequestMapping(value = "about",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getAboutWfe(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findDistinctByTaskSet_ToDepartmentIdOrTaskSet_FromDepartmentId(departmentId,departmentId,pageable);
        return wfes;
    }

    @RequestMapping(value = "operate",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "operate :doing pass refuse", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public ResponseEntity<?> operateWfe(@RequestParam("file") MultipartFile files[],
                                        @RequestParam("wfeId") String wfeId,
                                        @RequestParam("taskId") String taskId,
                                        @RequestParam("operate") String operate,
                                        @RequestParam("message") String message,
                                        HttpServletRequest request) {
        if(files.length>6){
            throw new DataFormatException("files length max 6");
        }
        //operate pass
        //operate refuse
        //operate recall
        //operate doing
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();

        Task task = taskRepository.findOne(taskId);
        Wfe wfe = wfeRepository.findOne(wfeId);

        if("doing".equals(operate)){
            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("WAIT");
            task.setMessage(message);

            String keyPrefix = organizationId + "_" + departmentId + "_" + userId + "_" + String.valueOf(System.currentTimeMillis());

            Set<Image> imageSet = asyncTaskService.save2Qiniu(files, keyPrefix,userId);
            asyncTaskService.uploadQiniu(keyPrefix,files);
            task.setImageSet(imageSet);

            taskRepository.save(task);

            Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());


            createTask.setToDepartmentId(task.getFromDepartmentId());
            createTask.setToDepartmentName(department.getName());

            createTask.setNodeType("TASK_NODE");
            createTask.setState("UNSTATE");
            createTask.setNextOperate("pass;refuse");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);
            wfe.setState("RUNNING");
            wfeRepository.save(wfe);

        }else if ("pass".equals(operate)) {
            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("PASS");
            task.setMessage(message);
            taskRepository.save(task);

            Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());

            createTask.setNodeType("END");
            createTask.setState("COMPLETED");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);

            wfe.setState("COMPLETED");
            wfeRepository.save(wfe);

        }else if ("refuse".equals(operate)) {
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
            createTask.setNextOperate("doing");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);

        }/*else if ("recall".equals(operate)) {
            task.setToUserId(userId);
            task.setToUserName(sysUser.getName());
            task.setState("COMPLETED");
            task.setMessage(message);
            taskRepository.save(task);

            Task createTask = new Task();
            createTask.setFromDepartmentId(department.getId());
            createTask.setFromDepartmentName(department.getName());
            createTask.setFromUserId(userId);
            createTask.setFromUserName(sysUser.getName());

            createTask.setNodeType("END");
            createTask.setState("COMPLETED");
            createTask.setWfe(wfe);

            taskRepository.save(createTask);
        }*/

        return ResponseEntity.created(URI.create(request.getRequestURI().concat(File.separator))).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED));
    }


}
