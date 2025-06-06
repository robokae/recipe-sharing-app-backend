package com.recipedb.api.controller;

import com.recipedb.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/featuredImage/{id}")
    public ResponseEntity<String> getImageById(@PathVariable String id) {
        return ResponseEntity.ok(imageService.getById(id));
    }
}
