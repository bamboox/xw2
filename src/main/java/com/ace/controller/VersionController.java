package com.ace.controller;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.Version;
import com.ace.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by sang on 2017/1/10.
 */
@ApiIgnore
//@Api
@RestController
@RequestMapping("/api/version")
public class VersionController {


    @Autowired
    private VersionRepository versionRepository;


    @GetMapping(value = "/{version:[\\s\\S]*}_{versionFix:[\\s\\S]*}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getCurrentFixVersion(@PathVariable("version") String version, @PathVariable("versionFix") String versionFix
    ) throws Exception {
        Version versionObj = versionRepository.findFirstByVersionOrderByGmtCreatedDesc(version);
        return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, versionObj));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getCurrentVersion(
    ) throws Exception {
        Version versionObj = versionRepository.findFirstByOrderByGmtCreatedDesc();
        return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, versionObj));
    }
}
