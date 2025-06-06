package com.recipedb.api.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecipeRequest {

    private String title;
    private String username;
    private String description;
    private MultipartFile featuredImage;
    private double completionTimeInMinutes;
    private double numServings;
    private String ingredients;
    private String instructions;
}
