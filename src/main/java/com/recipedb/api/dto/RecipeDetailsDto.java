package com.recipedb.api.dto;

import lombok.Getter;

@Getter
public class RecipeDetailsDto {

    private String title;
    private String description;
    private double completionTimeInMinutes;
    private double numServings;
    private String ingredients;
    private String instructions;
}
