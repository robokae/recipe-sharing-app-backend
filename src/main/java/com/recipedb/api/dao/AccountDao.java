package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountDao implements Dao<Account> {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String SELECT_BY_USERNAME = "select * from Account where username = :username";

    private static final String SELECT_ID_BY_USERNAME = "select id from Account where username = :username";

    private static final String INSERT_ACCOUNT = """
            insert into Account(username, password, role)
            values (:username, :password, :role)
            """;

    public Optional<Account> findByUsername(String username) {
        Account account = Account.builder().username(username).build();
        namedParams = new BeanPropertySqlParameterSource(account);

        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_USERNAME, namedParams,
                    new BeanPropertyRowMapper<>(Account.class)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Account save(Account account) {
        namedParams = new BeanPropertySqlParameterSource(account);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_ACCOUNT, namedParams, keyHolder);
        account.setId(keyHolder.getKey().intValue());
        return account;
    }

    @Override
    public void update(Account account) {}

    @Override
    public void delete(Account account) {}
}
