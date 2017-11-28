package com.ace.controller;

import com.ace.entity.SysUser;
import com.ace.service.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static springfox.documentation.spring.web.paths.RelativePathProvider.ROOT;

/**
 * @author bamboo
 */
@Controller
@RequestMapping("/api/image")
public class ImageController {
    @Value("${web.upload-path}")
    private String webUploadPath;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(value = "/view/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = sysUser.getId();
            String datdDirectory = String.valueOf(userId).concat(File.separator);
            String filePath = webUploadPath.concat(datdDirectory);
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT,filePath.concat(filename) ).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = sysUser.getId();

        if (!file.isEmpty()) {
            if (file.getContentType().contains("image")) {
                try {
                    String fileName = file.getOriginalFilename();
                    String extensionName = StringUtils.substringAfter(fileName, ".");
                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                    String datdDirectory = String.valueOf(userId).concat(File.separator);
                    String filePath = webUploadPath.concat(datdDirectory);
                    File dest = new File(filePath, newFileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    file.transferTo(dest);
                    return ResponseEntity.ok(newFileName);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body("");
                }
            }
        }
        return ResponseEntity.badRequest().body("");
    }
}
