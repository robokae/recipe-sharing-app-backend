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
public class ProfileSummary {

    private String username;
    private String firstName;
    private String lastName;
    private Date joinDate;
    private String description;
    private int numRecipes;
}
