package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import com.recipedb.api.model.Profile;
import com.recipedb.api.model.ProfileSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class ProfileDao implements Dao<Profile> {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    public static final String INSERT_PROFILE = """
            insert into Profile(id, accountId, firstName, lastName, email, description, createdAt)
            values (:id, :accountId, :firstName, :lastName, :email, :description, :createdAt)
            """;

    public static final String SELECT_PROFILE_SUMMARY_BY_USERNAME =
            "select * from ProfileSummary " +
            "where username = :username";

    public static final String SELECT_PROFILE_BY_ACCOUNT_ID = """
            select p.accountId, p.firstName, p.lastName, p.email, p.description, p.createdAt
            from Profile as p, Account as a
            where p.accountId = a.id and a.id = :id
            """;

    public ProfileSummary findSummaryByUsername(String username) {
        Account account = new Account();
        account.setUsername(username);
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.queryForObject(SELECT_PROFILE_SUMMARY_BY_USERNAME, namedParams,
                new BeanPropertyRowMapper<>(ProfileSummary.class));
    }

    public Profile findByAccountId(String accountId) {
        Account account = new Account();
        account.setId(accountId);
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.queryForObject(SELECT_PROFILE_BY_ACCOUNT_ID, namedParams, new BeanPropertyRowMapper<>(Profile.class));
    }

    @Override
    public Profile save(Profile profile) {
        namedParams = new BeanPropertySqlParameterSource(profile);
        jdbcTemplate.update(INSERT_PROFILE, namedParams);
        return profile;
    }

    @Override
    public Profile update(Profile profile) {
        return null;
    }

    @Override
    public void delete(Profile profile) {}
}
