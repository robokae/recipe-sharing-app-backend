package com.recipedb.api.controller;

import com.recipedb.api.model.RegistrationDetails;
import com.recipedb.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody RegistrationDetails registrationDetails) {
        accountService.createAccount(registrationDetails);
        return ResponseEntity.ok("Account created successfully");
    }
}
