package com.movienight.foundation.dto.pageable;

import com.movienight.foundation.constant.SortOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDto {

    private Integer pageNumber;
    private Integer countPerPage;
    private String sortBy;
    private SortOrder sortOrder;
}
