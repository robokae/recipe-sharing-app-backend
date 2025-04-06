package com.recipedb.api.controller;

import com.recipedb.api.exception.UserAlreadyExistException;
import com.recipedb.api.dto.RegisterDetailsDto;
import com.recipedb.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterDetailsDto registerDetails) {

        try {
            accountService.createAccount(registerDetails);
            return ResponseEntity.ok("Account created successfully");
        } catch(UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
    }
}
