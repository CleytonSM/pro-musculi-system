package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

@Data
public class PaginationDto {

    private String sortBy;
    private String sortType;
    private int pageSize;
    private int pageNumber;
}
