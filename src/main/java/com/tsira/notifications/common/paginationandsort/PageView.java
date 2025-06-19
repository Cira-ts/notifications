package com.tsira.notifications.common.paginationandsort;

import org.springframework.data.domain.Page;

import java.util.Collection;

public record PageView<T>(long totalRows, Collection<T> results) {

    public static <T> PageView<T> of(Page<T> results) {
        return new PageView<>(results.getTotalElements(), results.getContent());
    }
}
