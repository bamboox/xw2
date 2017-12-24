package com.ace.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Base64;

/**
 * size: file.size,
 * type: file.type,
 * name: file.name,
 * base64: base64
 * <p>
 * data:image/jpeg;base64,xxxx
 *
 * @author bamboo
 */
public class ImageBaser64 {
    private static Logger log = LoggerFactory.getLogger(JpushSdk.class);

    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            log.error("Image not found", e);
        } catch (IOException ioe) {
            log.error("Exception while reading the Image ", ioe);
        }
        return base64Image;
    }

    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            log.error("Image not found", e);
        } catch (IOException ioe) {
            log.error("Exception while reading the Image ", ioe);
        }
    }

    public static byte[] decoderToByte(String base64Image) {
        // Converting a Base64 String into Image byte array
        return Base64.getDecoder().decode(base64Image);
    }
}
