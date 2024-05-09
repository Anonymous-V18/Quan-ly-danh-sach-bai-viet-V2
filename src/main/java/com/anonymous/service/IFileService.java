package com.anonymous.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    String upload(MultipartFile multipartFile) throws IOException;

    void delete(String publicId) throws IOException;

    String filterUrlToGetPublicId(String url);

}
