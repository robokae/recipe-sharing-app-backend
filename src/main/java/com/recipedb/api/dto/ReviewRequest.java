package com.recipedb.api.dto;

import lombok.Data;

@Data
public class ReviewRequest {

    private String username;
    private String recipeId;
    private double rating;
    private String description;
}
