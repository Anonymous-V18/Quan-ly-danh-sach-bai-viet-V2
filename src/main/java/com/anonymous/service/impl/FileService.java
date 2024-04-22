package com.anonymous.service.impl;

import com.anonymous.service.IFileService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileService implements IFileService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            Map<?, ?> options = Map.of("resource_type", "auto");
            // "folder", "Test": add file to folder
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(), options)
                    .get("url")
                    .toString();
            /*
             * upload: limit 100 MB
             * uploadLarge: depend on max-request-size in file application.properties
             */
        } catch (IOException e) {
            throw new RuntimeException("Couldn't upload file !!!");
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            Map<?, ?> options = Map.of("invalidate", true);
            cloudinary.uploader().destroy(publicId, options);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't upload file !!!");
        }
    }

    @Override
    public String filterUrlToGetPublicId(String url) {
        int fileNameStart = url.lastIndexOf("/") + 1;
        int fileNameEnd = url.lastIndexOf(".");
        return url.substring(fileNameStart, fileNameEnd);
    }

}
