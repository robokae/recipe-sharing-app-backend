package com.recipedb.api.service;

import com.recipedb.api.dao.AccountDao;
import com.recipedb.api.dao.RecipeDao;
import com.recipedb.api.dto.RecipeRequest;
import com.recipedb.api.model.Account;
import com.recipedb.api.model.Image;
import com.recipedb.api.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private ImageService imageService;

    public void create(RecipeRequest recipeDetails) {
        accountDao.findByUsername(recipeDetails.getUsername()).map(Account::getId).ifPresent(accountId -> {
            Image featuredImage = imageService.saveImage(recipeDetails.getFeaturedImage());
            Recipe recipe = Recipe.builder()
                    .title(recipeDetails.getTitle()).accountId(accountId).createdAt(new Date())
                    .description(recipeDetails.getDescription())
                    .completionTimeInMinutes(recipeDetails.getCompletionTimeInMinutes())
                    .featuredImageId(featuredImage.getId())
                    .numServings(recipeDetails.getNumServings())
                    .ingredients(recipeDetails.getIngredients())
                    .instructions(recipeDetails.getInstructions()).build();
            recipeDao.save(recipe);
        });
    }

    public void update() {}

    public void get(int id) {}

    public List<Recipe> getAll() {
        return recipeDao.getAllRecipes();
    }

    public void delete(int id) {}
}
