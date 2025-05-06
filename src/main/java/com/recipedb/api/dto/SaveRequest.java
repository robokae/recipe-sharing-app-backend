package com.recipedb.api.dto;

import lombok.Data;

@Data
public class SaveRequest {

    private String action;
    private String recipeId;
    private String username;
}
