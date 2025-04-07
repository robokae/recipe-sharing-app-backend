package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import com.recipedb.api.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeDao implements Dao<Recipe> {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String INSERT_RECIPE = """
            insert into Recipe(title, accountId, createdAt, description, completionTimeInMinutes,
                numServings, ingredients, instructions)
            values(:title, :accountId, :createdAt, :description, :completionTimeInMinutes,
                :numServings, :ingredients, :instructions)
            """;

    private static final String SELECT_RECIPES_BY_ACCOUNT_ID = "select * from Recipe where accountId = :accountId";

    private static final String SELECT_ALL_RECIPES = "select * from Recipe";

    public List<Recipe> getAllByUsername(String username) {
        namedParams = new BeanPropertySqlParameterSource(Account.builder().username(username).build());
        return jdbcTemplate.query(SELECT_RECIPES_BY_ACCOUNT_ID, namedParams, new BeanPropertyRowMapper<>(Recipe.class));
    }

    public List<Recipe> getAllRecipes() {
        return jdbcTemplate.query(SELECT_ALL_RECIPES, new BeanPropertyRowMapper<>(Recipe.class));
    }

    @Override
    public void save(Recipe recipe) {
        namedParams = new BeanPropertySqlParameterSource(recipe);
        jdbcTemplate.update(INSERT_RECIPE, namedParams);
    }

    @Override
    public void update(Recipe recipe) {
    }

    @Override
    public void delete(Recipe recipe) {
    }
}
