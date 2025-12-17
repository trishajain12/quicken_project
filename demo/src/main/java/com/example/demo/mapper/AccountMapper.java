package com.example.demo.mapper;

import com.example.demo.model.Account;
import com.example.demo.resources.AccountResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResource toDto(Account entity);
    List<AccountResource> toDtoList(List<Account> allAccounts);
    // Account toEntity(AccountResource dto);
}
