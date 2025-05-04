package com.recipedb.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RecipeResponse {

    private String title;
    private String authorFirstName;
    private String authorLastName;
    private Date createdAt;
    private String description;
    private String featuredImageId;
    private String ingredients;
    private String instructions;
    private double completionTimeInMinutes;
    private double numServings;
    private double rating;
}
