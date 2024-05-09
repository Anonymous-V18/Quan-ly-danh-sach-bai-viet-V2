package com.anonymous.service.impl;

import com.anonymous.service.IFileService;
import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileService implements IFileService {

    Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {

        Map<?, ?> options = Map.of("resource_type", "auto");
        // "folder", "Test": add file to folder
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), options)
                .get("url")
                .toString();
        /*
         * upload: limit 100 MB
         * uploadLarge: depend on max-request-size in file application.yaml
         */

    }

    @Override
    public void delete(String publicId) throws IOException {
        Map<?, ?> options = Map.of("invalidate", true);
        cloudinary.uploader().destroy(publicId, options);
    }

    @Override
    public String filterUrlToGetPublicId(String url) {
        int fileNameStart = url.lastIndexOf("/") + 1;
        int fileNameEnd = url.lastIndexOf(".");
        return url.substring(fileNameStart, fileNameEnd);
    }

}
