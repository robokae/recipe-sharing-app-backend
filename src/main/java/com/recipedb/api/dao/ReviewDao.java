package com.recipedb.api.dao;

import com.recipedb.api.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDao implements Dao<Review> {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String INSERT_REVIEW = """
            insert into Review(id, accountId, recipeId, score, description, createdAt)
            values(:id, :accountId, :recipeId, :score, :description, :createdAt)
            """;

    private static final String SELECT_REVIEWS_BY_RECIPE_ID = "select * from Review where recipeId = :recipeId";

    public List<Review> findAllByRecipeId(String recipeId) {
        Review review = new Review();
        review.setRecipeId(recipeId);
        namedParams = new BeanPropertySqlParameterSource(review);
        return jdbcTemplate.query(SELECT_REVIEWS_BY_RECIPE_ID, namedParams, new BeanPropertyRowMapper<>(Review.class));
    }

    @Override
    public Review save(Review review) {
        namedParams = new BeanPropertySqlParameterSource(review);
        jdbcTemplate.update(INSERT_REVIEW, namedParams);
        return review;
    }

    @Override
    public Review update(Review review) {
        return null;
    }

    @Override
    public void delete(Review review) {}
}
