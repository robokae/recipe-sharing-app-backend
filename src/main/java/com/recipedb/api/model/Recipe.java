package com.recipedb.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    private String id;
    private String title;
    private String accountId;
    private String featuredImageId;
    private Date createdAt;
    private String description;
    private Double rating;
    private Double completionTimeInMinutes;
    private Double numServings;
    private String ingredients;
    private String instructions;
}
