package com.anonymous.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    String upload(MultipartFile multipartFile);

    void delete(String publicId);

    String filterUrlToGetPublicId(String url);

}
