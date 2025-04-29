package com.recipedb.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProfileResponse {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Date createdAt;
}
