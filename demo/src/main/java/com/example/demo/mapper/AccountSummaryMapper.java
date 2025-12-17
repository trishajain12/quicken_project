package com.example.demo.mapper;


import com.example.demo.model.AccountSummaryWithDateRange;
import com.example.demo.resources.AccountSummaryResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountSummaryMapper {

    AccountSummaryResource toDto(AccountSummaryWithDateRange entity);
    // AccountSummaryWithDateRange toEntity(AccountSummaryResource dto);
}
