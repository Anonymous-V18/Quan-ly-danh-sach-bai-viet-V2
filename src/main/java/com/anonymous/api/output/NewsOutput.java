package com.anonymous.api.output;

import com.anonymous.dto.NewsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsOutput {

    private Integer page;
    private Integer totalPages;
    List<NewsDTO> newsDtoList = new ArrayList<>();

}
