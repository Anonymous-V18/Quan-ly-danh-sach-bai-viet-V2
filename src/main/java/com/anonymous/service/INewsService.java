package com.anonymous.service;

import com.anonymous.dto.NewsDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewsService {

    NewsDTO save(NewsDTO newsDTO);

    void delete(Long[] ids);

    List<NewsDTO> findALL();

    List<NewsDTO> findALL(Pageable pageable);

    int totalItems();

}
