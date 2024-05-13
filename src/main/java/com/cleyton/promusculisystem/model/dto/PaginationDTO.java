package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

@Data
public class PaginationDTO {

    private String sortBy;
    private String sortType;
    private int pageSize;
    private int pageNumber;
}
