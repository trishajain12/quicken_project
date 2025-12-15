package com.example.demo.repository;

import com.example.demo.model.Account;
import com.example.demo.model.AccountDailySummary;
import com.example.demo.model.AccountSummaryWithDateRange;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Account> findAllAccounts() {
        String sql = """
                SELECT *
                FROM accounts
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Account(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                )
        );
    }

    public AccountSummaryWithDateRange getAccountSummary(long accountId, LocalDate from, LocalDate to) {
        String sql = """
            SELECT
                COALESCE(SUM(CASE WHEN amount > 0 THEN amount ELSE 0 END),0.00) AS total_income,
                COALESCE(SUM(CASE WHEN amount < 0 THEN -amount ELSE 0 END),0.00) AS total_expenses,
                COALESCE(SUM(amount),0.00) AS net
            FROM transactions
            WHERE account_id = ?
                AND date >= ?
                AND date <= ?
        """;

        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);

        return jdbcTemplate.queryForObject(sql, (rs,rowNum) ->
                        new AccountSummaryWithDateRange(
                            rs.getBigDecimal("total_income"),
                            rs.getBigDecimal("total_expenses"),
                            rs.getBigDecimal("net")
                        ),
                        accountId, fromDate, toDate
        );
    }

    public List<AccountDailySummary> getDailySummary(long accountId, LocalDate from, LocalDate to) {
        String sql = """
            SELECT
                date AS day_total
                COALESCE(SUM(CASE WHEN amount > 0 THEN amount ELSE 0 END),0.00) AS total_income,
                COALESCE(SUM(CASE WHEN amount < 0 THEN -amount ELSE 0 END),0.00) AS total_expenses,
                COALESCE(SUM(amount),0.00) AS net
            FROM transactions
            WHERE account_id = ?
                AND date >= ?
                AND date <= ?
            GROUP BY date
        """;

        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);

        // use query not query object we are returning multiple rows not just one per date range
        return jdbcTemplate.query(sql, (rs,rowNum) ->
                        new AccountDailySummary(
                                rs.getDate("day_total").toLocalDate(), // need LocalDate
                                rs.getBigDecimal("total_income"),
                                rs.getBigDecimal("total_expenses"),
                                rs.getBigDecimal("net")
                        ),
                accountId, fromDate, toDate
        );
    }
}
