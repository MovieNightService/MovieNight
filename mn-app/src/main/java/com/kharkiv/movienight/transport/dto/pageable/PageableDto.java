package com.kharkiv.movienight.transport.dto.pageable;

import com.kharkiv.movienight.service.utils.pageable.SortOrder;
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
