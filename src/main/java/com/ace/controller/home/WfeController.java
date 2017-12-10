package com.ace.controller.home;

import com.ace.entity.Wfe;
import com.ace.entity.user.SysUser;
import com.ace.repository.TaskRepository;
import com.ace.repository.WfeRepository;
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
 * Created by bamboo on 17-12-8.
 */
@RestController
@RequestMapping("/api/wfe")
public class WfeController {
    private TaskRepository taskRepository;
    private WfeRepository wfeRepository;

    @Autowired
    public WfeController(TaskRepository taskRepository, WfeRepository wfeRepository) {
        this.taskRepository = taskRepository;
        this.wfeRepository = wfeRepository;
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

        Page<Wfe> wfes = wfeRepository.findAllByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdIsNull(departmentId, pageable);

        return wfes;
    }

    @RequestMapping(value = "done",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getDoneTask(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                          Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findAllByTaskSet_ToDepartmentIdAndTaskSet_ToUserId(departmentId,userId, pageable);
        return wfes;
    }

    @RequestMapping(value = "create",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all WfeTask.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public Page<Wfe> getCreateTask(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                          Pageable pageable) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        String departmentId = sysUser.getDepartment().getId();

        Page<Wfe> wfes = wfeRepository.findAllByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdAndTaskSet_NodeType(departmentId,userId,"START", pageable);
        return wfes;
    }
}
