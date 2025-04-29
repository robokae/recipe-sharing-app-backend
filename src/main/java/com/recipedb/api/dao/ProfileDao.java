package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import com.recipedb.api.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProfileDao implements Dao<Profile> {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    public static final String INSERT_PROFILE = """
            insert into Profile(accountId, firstName, lastName, email, description, createdAt)
            values (:accountId, :firstName, :lastName, :email, :description, :createdAt)
            """;

    public static final String SELECT_PROFILE_BY_USERNAME = """
            select p.accountId, p.firstName, p.lastName, p.email, p.description, p.createdAt
            from Profile as p, Account as a
            where p.accountId = a.id and a.username = :username
            """;

    public Profile findByAccountId(String username) {
        Account account = Account.builder().username(username).build();
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.queryForObject(SELECT_PROFILE_BY_USERNAME, namedParams,
                new BeanPropertyRowMapper<>(Profile.class));
    }

    @Override
    public Profile save(Profile profile) {
        namedParams = new BeanPropertySqlParameterSource(profile);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_PROFILE, namedParams, keyHolder);
        profile.setId(keyHolder.getKey().intValue());
        return profile;
    }

    @Override
    public void update(Profile profile) {}

    @Override
    public void delete(Profile profile) {}
}
