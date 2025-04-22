package com.recipedb.api.controller;

import com.recipedb.api.dto.RecipeRequest;
import com.recipedb.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public ResponseEntity<?> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @PostMapping(value = "/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRecipe(@RequestPart(value = "featuredImage", required = false) MultipartFile featuredImage,
                                          @RequestPart(value = "recipeRequest") RecipeRequest recipeRequest) {
        recipeService.create(recipeRequest);
        return ResponseEntity.ok("Recipe created");
    }
}
