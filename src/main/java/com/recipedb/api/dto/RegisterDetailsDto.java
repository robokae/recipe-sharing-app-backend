package com.recipedb.api.dto;

import lombok.Data;

@Data
public class RegisterDetailsDto {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
