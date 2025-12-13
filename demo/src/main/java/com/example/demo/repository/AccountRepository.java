package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Account> findAllAccounts() {
        String sql = """
                SELECT * FROM accounts
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Account(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                )
        );
    }
}
