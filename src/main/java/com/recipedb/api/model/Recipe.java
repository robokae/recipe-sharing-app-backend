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
    private int id;
    private String title;
    private Integer accountId;
    private Integer featuredImageId;
    private Date createdAt;
    private String description;
    private Double rating;
    private double completionTimeInMinutes;
    private double numServings;
    private String ingredients;
    private String instructions;
}
