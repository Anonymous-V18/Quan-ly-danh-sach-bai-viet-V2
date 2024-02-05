package com.anonymous.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDTO extends AbstractDTO<NewsDTO> {
    private String title;
    private String content;
    private String shortDescription;
    private String categoryCode;
    private String thumbnail;

}
