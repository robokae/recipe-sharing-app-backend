package com.recipedb.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private String id;
    private String accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Date createdAt;
}
