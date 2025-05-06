package com.recipedb.api.controller;

import com.recipedb.api.dto.RecipePreviewResponse;
import com.recipedb.api.dto.RecipeRequest;
import com.recipedb.api.dto.RecipeResponse;
import com.recipedb.api.dto.SaveRequest;
import com.recipedb.api.model.Recipe;
import com.recipedb.api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    private final static String FEATURED_IMAGE = "featuredImage";

    private final static String RECIPE_REQUEST = "recipeRequest";

    @GetMapping("/recipes")
    public ResponseEntity<?> getLatestRecipes() {
        return ResponseEntity.ok(recipeService.getLatestRecipes());
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable String id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @GetMapping("/recipes/{username}")
    public ResponseEntity<List<RecipePreviewResponse>> getRecipesForUser(@PathVariable String username) {
        return ResponseEntity.ok(recipeService.getRecipesForUser(username));
    }

    @GetMapping("/recipes/saved/{username}")
    public ResponseEntity<List<RecipePreviewResponse>> getSavedRecipesForUser(@PathVariable String username) {
        return ResponseEntity.ok(recipeService.getSavedRecipesForUser(username));
    }

    @PostMapping(value = "/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRecipe(
            @RequestPart(value = FEATURED_IMAGE, required = false) MultipartFile featuredImage,
            @RequestPart(value = RECIPE_REQUEST) RecipeRequest recipeRequest) {

        Optional.ofNullable(featuredImage).ifPresent(recipeRequest::setFeaturedImage);
        recipeService.create(recipeRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/recipe/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable String id,
            @RequestPart(value = FEATURED_IMAGE, required = false) MultipartFile featuredImage,
            @RequestPart(value = RECIPE_REQUEST) Map<String, Object> recipeRequest) {

        Optional.ofNullable(featuredImage).ifPresent(image -> recipeRequest.put(FEATURED_IMAGE, image));
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeRequest));
    }

    @PostMapping(value = "/recipe")
    public ResponseEntity<?> save(@RequestBody SaveRequest saveRequest) {
        recipeService.saveRecipe(saveRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/recipe/{recipeId}/user/{username}")
    public ResponseEntity<?> deleteSave(@PathVariable String recipeId, @PathVariable String username) {
        recipeService.deleteSave(recipeId, username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
