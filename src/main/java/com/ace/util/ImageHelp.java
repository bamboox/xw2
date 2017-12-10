package com.ace.util;

import com.ace.entity.file.Image;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-11-28.
 */
public class ImageHelp {
    public static Set<Image> save2Disk(MultipartFile files[], String webUploadPath, String organizationId, String departmentId, String userId) {
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

}
