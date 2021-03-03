package com.movienight.app.service.utils.pageable;

import com.movienight.app.exception.common.BadRequestException;
import com.movienight.foundation.dto.pageable.PageableDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageableService {

    static Pageable getPageable(PageableDto pageableDto) {
        if (pageableDto != null) {
            Integer pageNumber = pageableDto.getPageNumber();
            Integer countPerPage = pageableDto.getCountPerPage();

            if(pageNumber != null && countPerPage != null) {
                if (pageableDto.getSortBy() == null) {
                    return PageRequest.of(pageNumber, countPerPage);
                }

                Sort sort = Sort.by(pageableDto.getSortBy());

                if (pageableDto.getSortOrder() != null) {
                    switch (pageableDto.getSortOrder()) {
                        case ASC -> sort = sort.ascending();
                        case DESC -> sort = sort.descending();
                        default -> throw new BadRequestException("Incorrect SortOrder type");
                    }
                }

                return PageRequest.of(pageNumber, countPerPage, sort);
            }

            return Pageable.unpaged();
        }
        throw new BadRequestException("Pageable must be not null");
    }
}
