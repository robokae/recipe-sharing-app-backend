package com.recipedb.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RecipePreviewResponse {

    private String id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private Date createdAt;
    private String featuredImageId;
    private String description;
    private double rating;
}
