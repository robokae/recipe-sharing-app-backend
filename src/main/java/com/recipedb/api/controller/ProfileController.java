package com.recipedb.api.controller;

import com.recipedb.api.model.Profile;
import com.recipedb.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{username}")
    public ResponseEntity<Profile> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getByUsername(username));
    }
}
