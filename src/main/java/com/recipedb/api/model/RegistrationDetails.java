package com.recipedb.api.model;

import lombok.Data;

@Data
public class RegistrationDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
