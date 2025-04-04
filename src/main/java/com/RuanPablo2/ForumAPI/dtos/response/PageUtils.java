package com.RuanPablo2.ForumAPI.dtos.response;

import org.springframework.data.domain.Page;

public class PageUtils {

    private PageUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> PageResponseDTO<T> buildPageResponse(Page<T> page) {
        return new PageResponseDTO<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}