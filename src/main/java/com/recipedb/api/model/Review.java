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
public class Review {

    private String id;
    private String accountId;
    private String recipeId;
    private double score;
    private String description;
    private Date createdAt;
}
