package com.anonymous.api;

import com.anonymous.dto.NewsDTO;
import com.anonymous.dto.response.ApiResponse;
import com.anonymous.dto.response.NewsOutput;
import com.anonymous.service.IFileService;
import com.anonymous.service.INewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsAPI {

    INewsService newsService;
    IFileService fileService;


    @GetMapping("/news")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<NewsOutput> getNews(@RequestParam("page") Integer page,
                                           @RequestParam("limit") Integer limit) {
        NewsOutput newsOutput = new NewsOutput();
        if (page != null && limit != null) {
            newsOutput.setPage(page);
            newsOutput.setTotalPages((int) (Math.ceil(((double) newsService.totalItems()) / limit)));
            Pageable pageable = PageRequest.of(page - 1, limit);
            newsOutput.setNewsDtoList(newsService.findALL(pageable));
        } else {
            newsOutput.setNewsDtoList(newsService.findALL());
        }
        return ApiResponse.<NewsOutput>builder().result(newsOutput).build();
    }

    @PostMapping("/news")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<NewsDTO> insert(@RequestParam("newsTextDTO") String newsTextDTO,
                                       @RequestParam("file") MultipartFile file)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewsDTO newsDTO = objectMapper.readValue(newsTextDTO, NewsDTO.class);
        String thumbnailUrl = fileService.upload(file);
        newsDTO.setThumbnail(thumbnailUrl);
        newsDTO = newsService.save(newsDTO);
        return ApiResponse.<NewsDTO>builder().result(newsDTO).build();
    }

    @PutMapping("/news/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<NewsDTO> update(@RequestParam("newsTextDTO") String newsTextDTO,
                                       @RequestParam("file") MultipartFile file,
                                       @PathVariable(value = "id") Long id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewsDTO newsDTO = objectMapper.readValue(newsTextDTO, NewsDTO.class);
        if (!file.isEmpty()) {
            String oldThumbnailUrl = newsDTO.getThumbnail();
            if (!oldThumbnailUrl.isBlank()) {
                String publicId = fileService.filterUrlToGetPublicId(oldThumbnailUrl);
                fileService.delete(publicId);
            }
            String thumbnailUrl = fileService.upload(file);
            newsDTO.setThumbnail(thumbnailUrl);
        }
        newsDTO = newsService.save(newsDTO);
        return ApiResponse.<NewsDTO>builder().result(newsDTO).build();
    }

    @DeleteMapping("/news")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> delete(@RequestBody Long[] id) {
        newsService.delete(id);
        return ApiResponse.<String>builder().message("Successfully deleted !").build();
    }


}
