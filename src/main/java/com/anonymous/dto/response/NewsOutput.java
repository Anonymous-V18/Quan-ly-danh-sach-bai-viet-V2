package com.anonymous.dto.response;

import com.anonymous.dto.NewsDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsOutput {

    private Integer page;
    private Integer totalPages;
    private List<NewsDTO> newsDtoList = new ArrayList<>();

}
