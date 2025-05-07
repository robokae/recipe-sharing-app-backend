package com.recipedb.api.controller;

import com.recipedb.api.dto.ReviewRequest;
import com.recipedb.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews/{recipeId}")
    public ResponseEntity<?> getReviewsForRecipe(@PathVariable String recipeId) {
        return ResponseEntity.ok(reviewService.getAllForRecipe(recipeId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewService.create(reviewRequest));
    }
}
