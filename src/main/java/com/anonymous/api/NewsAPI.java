package com.anonymous.api;

import com.anonymous.api.output.NewsOutput;
import com.anonymous.dto.NewsDTO;
import com.anonymous.service.IFileService;
import com.anonymous.service.INewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NewsAPI {

    @Autowired
    private INewsService newsService;

    @Autowired
    private IFileService fileService;


    @GetMapping("/news")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public NewsOutput getNews(@RequestParam("page") Integer page,
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
        return newsOutput;
    }

    @PostMapping("/news")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public NewsDTO insert(@RequestParam("newsTextDTO") String newsTextDTO,
                          @RequestParam("file") MultipartFile file)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewsDTO newsDTO = objectMapper.readValue(newsTextDTO, NewsDTO.class);
        String thumbnailUrl = fileService.upload(file);
        newsDTO.setThumbnail(thumbnailUrl);
        return newsService.save(newsDTO);
    }

    @PutMapping("/news/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public NewsDTO update(@RequestParam("newsTextDTO") String newsTextDTO,
                          @RequestParam("file") MultipartFile file,
                          @PathVariable(value = "id") Long id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewsDTO newsDTO = objectMapper.readValue(newsTextDTO, NewsDTO.class);
        if (!file.isEmpty()) {
            String OldThumbnailUrl = newsDTO.getThumbnail();
            if (!OldThumbnailUrl.isBlank()) {
                String publicId = fileService.filterUrlToGetPublicId(OldThumbnailUrl);
                fileService.delete(publicId);
            }
            String thumbnailUrl = fileService.upload(file);
            newsDTO.setThumbnail(thumbnailUrl);
        }
        newsDTO.setId(id);
        return newsService.save(newsDTO);
    }

    @DeleteMapping("/news")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody Long[] id) {
        newsService.Delete(id);
    }

}
