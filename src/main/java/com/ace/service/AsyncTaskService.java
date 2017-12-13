package com.ace.service;

import com.ace.entity.file.Image;
import com.ace.util.QiNiu;
import com.google.common.io.ByteStreams;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-12-13.
 */
@Service
public class AsyncTaskService {
    public final static QiNiu qiNiu = new QiNiu();

    public Set<Image> save2Disk(MultipartFile files[], String webUploadPath, String organizationId, String departmentId, String userId) {
        Set<Image> imageSet = new HashSet<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                if (file.getContentType().contains("image")) {
                    try {
                        String fileName = file.getOriginalFilename();
                        String extensionName = StringUtils.substringAfter(fileName, ".");
                        String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                        String dataDirectory = organizationId.concat(File.separator).concat(departmentId).concat(File.separator).concat(userId).concat(File.separator);
                        String filePath = webUploadPath.concat(dataDirectory);
                        File dest = new File(filePath, newFileName);
                        if (!dest.getParentFile().exists()) {
                            dest.getParentFile().mkdirs();
                        }
                        file.transferTo(dest);
                        String imageUrl = "/api/image/".concat(dataDirectory).concat(newFileName);
                        Image image = new Image();
                        image.setUrl(userId);
                        image.setUrl(imageUrl);
                        image.setUserId(userId);
                        imageSet.add(image);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        }
        return imageSet;
    }

    public Set<Image> save2Qiniu(MultipartFile files[], String keyPrefix, String userId) {
        Set<Image> imageSet = new HashSet<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                if (file.getContentType().contains("image")) {
                    Image image = new Image();
                    image.setUrl("http://orahxdcid.bkt.clouddn.com/".concat(keyPrefix).concat(String.valueOf(i)));
                    image.setUserId(userId);
                    imageSet.add(image);
                }
            }
        }
        return imageSet;
    }

    @Async
    public void uploadQiniu(String keyPrefix, MultipartFile files[]) {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                if (file.getContentType().contains("image")) {
                    try {
                        // Lambda Runnable
                        qiNiu.upload(ByteStreams.toByteArray(file.getInputStream()), keyPrefix.concat(String.valueOf(i)));
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
