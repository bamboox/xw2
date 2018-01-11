package com.ace.controller.news;

import com.ace.common.base.ApiBaseFileReqParam;
import com.ace.common.base.ApiBaseReqParam;
import com.ace.common.base.ApiBaseResponse;
import com.ace.common.base.BaseController;
import com.ace.entity.News;
import com.ace.entity.file.Image;
import com.ace.entity.user.Department;
import com.ace.entity.user.SysUser;
import com.ace.repository.DepartmentRepository;
import com.ace.repository.NewsRepository;
import com.ace.repository.SysUserRepository;
import com.ace.service.AsyncTaskService;
import com.ace.service.MsgService;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sang on 2017/1/10.
 */
@Api
@RestController
@RequestMapping("/api/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MsgService msgService;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private AsyncTaskService asyncTaskService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<?> submit(@RequestBody ApiBaseReqParam<ApiNewsReqParam> apiNewsReqParam) throws Exception {

        /*SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();*/

        initBaseInfo();

        ApiNewsReqParam bizParams = apiNewsReqParam.getBizParams();
        String toDepartmentJson = bizParams.getToDepartmentJson();
        List<ApiBaseFileReqParam> files = bizParams.getFiles();

        String toDepartment = "";

        if ("ALL".equals(toDepartmentJson)) {
            //
            List<Department> allDepartment = departmentRepository.findAllByOrganization_Id(organizationId);
            List<String> ids = allDepartment.stream().map(Department::getId).collect(Collectors.toList());
            String join = Joiner.on(",").join(ids);
            toDepartment = join;
        } else {
            //xxxx,xxxx
            toDepartment = toDepartmentJson;
        }

        News news = new News();
        news.setContext(bizParams.getContext());
        news.setTitle(bizParams.getTitle());
        news.setToDepartmentJson(toDepartment);
        news.setFromUserId(userId);
        news.setFromDepartmentId(departmentId);
        news.setOrganizationId(organizationId);

        String keyPrefix = organizationId + "_" + departmentId + "_" + userId + "_" + String.valueOf(
                System.currentTimeMillis());

        Set<Image> imageSet = asyncTaskService.save2Qiniu(files, keyPrefix, userId);
        asyncTaskService.uploadQiniu(keyPrefix, files);

        news.setFiles(Joiner.on(",").join(imageSet.stream().map(Image::getUrl).collect(Collectors.toList())));

        newsRepository.save(news);
        //
        String context = department.getName() + "部门发起资讯!";
        msgService.sendMsgByTag(context, "您有新资讯来了!", ImmutableMap.of("id", news.getId(), "activity", "news"),
                toDepartment.split(","));
        return ResponseEntity.created(URI.create("")).body(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, "succeed"));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single news.", notes = "You have to provide a valid news ID.")
    @ResponseBody
    public ResponseEntity<?> getNews(@ApiParam(value = "The ID of the Wfe.", required = true)
                                     @PathVariable("id") String id
    ) throws Exception {
        return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, newsRepository.getOne(id)));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all News.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 20) more ?page=0&size=20&sort=b&sort=a,desc&sort=c,desc ")
    @ResponseBody
    public ResponseEntity<?> getNewsAll(@PageableDefault(value = 20, sort = {"gmtCreated"}, direction = Sort.Direction.DESC)
                                                Pageable pageable, @RequestParam(value = "context", defaultValue = "") String context) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String oId = sysUser.getDepartment().getOrganization().getId();
        Department department = sysUser.getDepartment();
        if (department.getTypeCode().equals("00000")) {
            return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, newsRepository.findAllByOrganizationIdAndTitleContainingOrContextContaining(oId, context, context, pageable)));
        } else {
            return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, newsRepository.findAllByOrganizationIdAndToDepartmentJsonContainingAndTitleContainingOrContextContaining(oId, department.getId(), context, context, pageable)));
        }
    }

}
