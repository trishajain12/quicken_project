package com.example.demo.mapper;

import com.example.demo.model.AccountDailySummary;
import com.example.demo.resources.AccountDailySummaryResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountDailySummaryMapper {
    AccountDailySummaryResource toDto(AccountDailySummary entity);
    AccountDailySummary toEntity(AccountDailySummaryResource dto);
}


