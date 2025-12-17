package com.example.demo.mapper;

import com.example.demo.model.AccountDailySummary;
import com.example.demo.resources.AccountDailySummaryResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountDailySummaryMapper {
    AccountDailySummaryResource toDto(AccountDailySummary entity);
    List<AccountDailySummaryResource> toDtoList(List<AccountDailySummary> dailySummary);
    // AccountDailySummary toEntity(AccountDailySummaryResource dto);
}


