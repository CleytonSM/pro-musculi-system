package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.dto.PaginationDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ModelMapperHelper {

    public Sort.Direction getSortType(final PaginationDto paginationDto) {
        return Optional.ofNullable(paginationDto.getSortType()).map(sortType ->
                Sort.Direction.valueOf(sortType.toString())).orElse(Sort.Direction.ASC);
    }
}
