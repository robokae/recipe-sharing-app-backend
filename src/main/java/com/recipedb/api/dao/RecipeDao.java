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
            insert into Recipe(id, title, accountId, createdAt, description, completionTimeInMinutes, featuredImageId,
                numServings, ingredients, instructions)
            values(:id, :title, :accountId, :createdAt, :description, :completionTimeInMinutes, :featuredImageId,
                :numServings, :ingredients, :instructions)
            """;

    private static final String SELECT_RECIPES_BY_USERNAME = """
            select r.id, r.title, r.accountId, r.createdAt, r.description, r.featuredImageId, r.completionTimeInMinutes,
                r.numServings, r.ingredients, r.instructions
            from Recipe as r, Account as a
            where r.accountId = a.id and a.username = :username
            """;

    private static final String UPDATE_RECIPE_BY_ID = """
            update Recipe as r
            set r.title = :title, r.description = :description, r.featuredImageId = :featuredImageId,
                r.completionTimeInMinutes = :completionTimeInMinutes, r.numServings = :numServings,
                r.ingredients = :ingredients, r.instructions = :instructions
            where r.id = :id
            """;

    private static final String SELECT_RECIPE_BY_ID = "select * from Recipe where id = :id";

    private static final String SELECT_LATEST_RECIPES = "select * from Recipe order by createdAt desc";

    public List<Recipe> getAllByUsername(String username) {
        Account account = Account.builder().username(username).build();
        namedParams = new BeanPropertySqlParameterSource(account);
        return jdbcTemplate.query(SELECT_RECIPES_BY_USERNAME, namedParams, new BeanPropertyRowMapper<>(Recipe.class));
    }

    public List<Recipe> getLatestRecipes() {
        return jdbcTemplate.query(SELECT_LATEST_RECIPES, new BeanPropertyRowMapper<>(Recipe.class));
    }

    public Recipe getRecipeById(String id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        namedParams = new BeanPropertySqlParameterSource(recipe);
        return jdbcTemplate.queryForObject(SELECT_RECIPE_BY_ID, namedParams,
                new BeanPropertyRowMapper<>(Recipe.class));
    }

    @Override
    public Recipe save(Recipe recipe) {
        namedParams = new BeanPropertySqlParameterSource(recipe);
        jdbcTemplate.update(INSERT_RECIPE, namedParams);
        return recipe;
    }

    @Override
    public Recipe update(Recipe recipe) {
        namedParams = new BeanPropertySqlParameterSource(recipe);
        jdbcTemplate.update(UPDATE_RECIPE_BY_ID, namedParams);
        return recipe;
    }

    @Override
    public void delete(Recipe recipe) {
    }
}
