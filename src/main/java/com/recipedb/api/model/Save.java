package com.recipedb.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Save {

    private String recipeId;
    private String accountId;
    private Date createdAt;
}
