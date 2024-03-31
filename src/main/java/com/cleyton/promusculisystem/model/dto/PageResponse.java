package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    //private PaginationDto pagination;
    private long total;
    private List<T> records;
}
