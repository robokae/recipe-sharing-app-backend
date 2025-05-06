package com.recipedb.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipePreview {

    private String id;
    private String title;
    private String username;
    private String firstName;
    private String lastName;
    private Date createdAt;
    private String featuredImageId;
    private String description;
    private Double rating;
}
