package com.recipedb.api.dao;

import com.recipedb.api.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ImageDao implements Dao<Image> {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource namedParams;

    private static final String INSERT_IMAGE = """
            insert into FeaturedImage(fileName, data, uploadDate)
            values(:fileName, :data, :uploadDate)
            """;

    private static final String SELECT_IMAGE_BY_ID = "select * from FeaturedImage where id = :id";

    public Image getById(Integer id) {
        Image image = new Image();
        image.setId(id);
        namedParams = new BeanPropertySqlParameterSource(image);
        return jdbcTemplate.queryForObject(SELECT_IMAGE_BY_ID, namedParams, new BeanPropertyRowMapper<>(Image.class));
    }

    @Override
    public Image save(Image image) {
        namedParams = new BeanPropertySqlParameterSource(image);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_IMAGE, namedParams, keyHolder);
        image.setId(keyHolder.getKey().intValue());
        return image;
    }

    @Override
    public void update(Image image) {

    }

    @Override
    public void delete(Image image) {}
}
