package com.recipedb.api.dao;

import com.recipedb.api.exception.DAOException;
import com.recipedb.api.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;


@Component
public class AccountDao implements Dao<Account> {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String SELECT_BY_USERNAME = "select * from Account where username = :username";

    private static final String SELECT_BY_ID = "select * from Account where id = :id";

    private static final String SELECT_ID_BY_USERNAME = "select id from Account where username = :username";

    private static final String INSERT_ACCOUNT = """
            insert into Account(id, username, password, role)
            values (:id, :username, :password, :role)
            """;

    public Account findByUsername(String username) {
        Account account = new Account();
        account.setUsername(username);
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.query(SELECT_BY_USERNAME, namedParams, new BeanPropertyRowMapper<>(Account.class))
                .stream().findFirst().orElse(null);
    }

    public Account findById(String id) {
        Account account = new Account();
        account.setId(id);
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.query(SELECT_BY_ID, namedParams, new BeanPropertyRowMapper<>(Account.class))
                .stream().findFirst()
                .orElseThrow(() -> new DAOException("Account not found"));
    }

    @Override
    public Account save(Account account) {
        namedParams = new BeanPropertySqlParameterSource(account);
        jdbcTemplate.update(INSERT_ACCOUNT, namedParams);
        return account;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public void delete(Account account) {}
}
