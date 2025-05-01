package com.drsimple.jwtsecurity.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {

    public Pageable createPageRequest(int page, int size, String sortBy, String sortDir) {
        // Adjust to 0-based index
        int adjustedPage = (page < 1) ? 0 : page - 1;

        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDir).orElse(Sort.Direction.ASC);
        return PageRequest.of(adjustedPage, size, Sort.by(direction, sortBy));
    }
}