package com.recipedb.api.controller;

import com.recipedb.api.dto.RecipeDetailsDto;
import com.recipedb.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public ResponseEntity<?> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @PostMapping("/recipe")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeDetailsDto recipeDetails) {
        recipeService.create(recipeDetails);
        return ResponseEntity.ok("Recipe created");
    }
}
