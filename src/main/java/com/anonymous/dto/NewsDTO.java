package com.anonymous.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDTO extends AbstractDTO<NewsDTO> {

    String title;
    String content;
    String shortDescription;
    String categoryCode;
    String thumbnail;

}
