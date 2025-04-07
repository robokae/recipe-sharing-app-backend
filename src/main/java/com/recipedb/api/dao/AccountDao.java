package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountDao implements Dao<Account> {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String SELECT_BY_USERNAME = "select * from Account where username = :username";

    private static final String INSERT_ACCOUNT = """
            insert into Account(firstName, lastName, username, password, role, email)
            values (:firstName, :lastName, :username, :password, :role, :email)
            """;

    public Optional<Account> findByUsername(String username) {
        namedParams = new BeanPropertySqlParameterSource(Account.builder().username(username).build());

        try {
            Account account = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, namedParams,
                    new BeanPropertyRowMapper<>(Account.class));
            return Optional.of(account);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Account account) {
        namedParams = new BeanPropertySqlParameterSource(account);
        jdbcTemplate.update(INSERT_ACCOUNT, namedParams);
    }

    @Override
    public void update(Account account) {}

    @Override
    public void delete(Account account) {}
}
