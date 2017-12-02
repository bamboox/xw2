package com.ace.controller.file;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.Department;
import com.ace.entity.Image;
import com.ace.entity.SysUser;
import com.ace.repository.ImageRepository;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.runtime.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

import static springfox.documentation.spring.web.paths.RelativePathProvider.ROOT;

/**
 * @author bamboo
 */
@Api(value = "图片controller", description = "图片操作")
@Controller
@RequestMapping("/api/image")
public class ImageController {
    @Value("${web.upload-path}")
    private String webUploadPath;
    private final ResourceLoader resourceLoader;
    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ResourceLoader resourceLoader, ImageRepository imageRepository) {
        this.resourceLoader = resourceLoader;
        this.imageRepository = imageRepository;
    }

    @RequestMapping(value = "/view/{organizationId}/{departmentId}/{userId}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String organizationId,
                                     @PathVariable String departmentId,
                                     @PathVariable String userId,
                                     @PathVariable String filename) {
        try {
            String datdDirectory = organizationId.concat(File.separator).concat(departmentId).concat(File.separator).concat(userId).concat(File.separator);
            String filePath = webUploadPath.concat(datdDirectory);
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filePath.concat(filename)).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = sysUser.getId();
        Department department = sysUser.getDepartment();
        String departmentId = sysUser.getDepartment().getId();
        String organizationId = department.getOrganization().getId();

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
                    String imageUrl = File.separator.concat(datdDirectory).concat(newFileName);
                    Image image = new Image();
                    image.setUrl(sysUser.getId());
                    image.setUrl(imageUrl);
                    imageRepository.save(image);

                    return ResponseEntity.created(URI.create(imageUrl)).body(ApiBaseResponse.fromHttpStatus(HttpStatus.CREATED, imageUrl));
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body("");
                }
            }
        }
        return ResponseEntity.badRequest().body("");
    }
}
