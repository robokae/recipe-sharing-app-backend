package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import com.recipedb.api.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeDao implements Dao<Recipe> {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String INSERT_RECIPE = """
            insert into Recipe(title, accountId, createdAt, description, completionTimeInMinutes, featuredImageId,
                numServings, ingredients, instructions)
            values(:title, :accountId, :createdAt, :description, :completionTimeInMinutes, :featuredImageId,
                :numServings, :ingredients, :instructions)
            """;

    private static final String SELECT_RECIPES_BY_ACCOUNT_ID = """
            select r.title, r.accountId, r.createdAt, r.description, r.completionTimeInMinutes, r.numServings,
                r.ingredients, r.instructions"
            from Recipe as r, Account as a
            where r.accountId = a.id and a.username = :username
            """;

    private static final String SELECT_ALL_RECIPES = "select * from Recipe";

    public List<Recipe> getAllByUsername(String username) {
        Account account = Account.builder().username(username).build();
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.query(SELECT_RECIPES_BY_ACCOUNT_ID, namedParams, new BeanPropertyRowMapper<>(Recipe.class));
    }

    public List<Recipe> getAllRecipes() {
        return jdbcTemplate.query(SELECT_ALL_RECIPES, new BeanPropertyRowMapper<>(Recipe.class));
    }

    @Override
    public Recipe save(Recipe recipe) {
        namedParams = new BeanPropertySqlParameterSource(recipe);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_RECIPE, namedParams, keyHolder);
        recipe.setId(keyHolder.getKey().intValue());
        return recipe;
    }

    @Override
    public void update(Recipe recipe) {
    }

    @Override
    public void delete(Recipe recipe) {
    }
}
