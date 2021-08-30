package com.koreait.day4.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
    private Integer totalPages;         // 몇 페이지인지
    private Long totalElements;         // 요소 개수
    private Integer currentPage;        // 현재 페이지
    private Integer currentElements;    // 현재 요소
}
