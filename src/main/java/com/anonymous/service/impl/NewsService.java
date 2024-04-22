package com.anonymous.service.impl;

import com.anonymous.converter.NewsConverter;
import com.anonymous.dto.NewsDTO;
import com.anonymous.entity.CategoryEntity;
import com.anonymous.entity.NewsEntity;
import com.anonymous.repository.ICategoryRepository;
import com.anonymous.repository.INewsRepository;
import com.anonymous.service.INewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsService implements INewsService {

    INewsRepository newsRepository;
    ICategoryRepository categoryRepository;
    NewsConverter newsConverter;

    @Override
    public NewsDTO save(NewsDTO newsDTO) {
        NewsEntity newsEntity;
        if (newsDTO.getId() != null) {
            NewsEntity oldNewsEntity = newsRepository.findOneById(newsDTO.getId());
            newsEntity = newsConverter.toEntity(newsDTO, oldNewsEntity);
        } else {
            newsEntity = newsConverter.toEntity(newsDTO);
        }
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(newsDTO.getCategoryCode());
        newsEntity.setCategory(categoryEntity);
        newsEntity = newsRepository.save(newsEntity);
        return newsConverter.toDTO(newsEntity);
    }

    @Override
    public void Delete(Long[] Ids) {
        newsRepository.deleteAllById(Arrays.stream(Ids).toList());
    }

    @Override
    public List<NewsDTO> findALL() {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        List<NewsEntity> newsEntityList = newsRepository.findAll();
        for (NewsEntity newsEntity : newsEntityList) {
            NewsDTO newsDTO = newsConverter.toDTO(newsEntity);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }

    @Override
    public List<NewsDTO> findALL(Pageable pageable) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        List<NewsEntity> newsEntityList = newsRepository.findAll(pageable).getContent();
        for (NewsEntity newsEntity : newsEntityList) {
            NewsDTO newsDTO = newsConverter.toDTO(newsEntity);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }

    @Override
    public int totalItems() {
        return (int) newsRepository.count();
    }

}
