package com.recipedb.api.controller;

import com.recipedb.api.dto.RegisterRequest;
import com.recipedb.api.model.Profile;
import com.recipedb.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Profile> registerAccount(@RequestBody RegisterRequest registerDetails) {
        Profile profile = accountService.createAccount(registerDetails);
        return ResponseEntity.ok(profile);
    }
}
