package com.anonymous.converter;

import com.anonymous.dto.NewsDTO;
import com.anonymous.entity.NewsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter {
    @Autowired
    private ModelMapper mapper;

    public NewsEntity toEntity(NewsDTO newsDTO) {
        return mapper.map(newsDTO, NewsEntity.class);
    }

    public NewsDTO toDTO(NewsEntity newsEntity) {
        return mapper.map(newsEntity, NewsDTO.class);
    }

    public NewsEntity toEntity(NewsDTO newsDTO, NewsEntity oldNewsEntity) {
        oldNewsEntity.setTitle(newsDTO.getTitle());
        oldNewsEntity.setThumbnail(newsDTO.getThumbnail());
        oldNewsEntity.setShortDescription(newsDTO.getShortDescription());
        oldNewsEntity.setContent(newsDTO.getContent());
        return oldNewsEntity;
    }

}