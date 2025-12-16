package com.example.demo.mapper;

import com.example.demo.model.Account;
import com.example.demo.resources.AccountResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResource toDto(Account entity);
    Account toEntity(AccountResource dto);
}
