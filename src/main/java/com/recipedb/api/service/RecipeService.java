package com.recipedb.api.service;

import com.recipedb.api.dao.RecipeDao;
import com.recipedb.api.model.RecipePreview;
import com.recipedb.api.dto.RecipeRequest;
import com.recipedb.api.dto.RecipeResponse;
import com.recipedb.api.dto.SaveRequest;
import com.recipedb.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class RecipeService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ImageService imageService;

    public void create(RecipeRequest recipeDetails) {
        String accountId = accountService.getAccountId(recipeDetails.getUsername());

        Recipe.RecipeBuilder recipeBuilder = Recipe.builder();

        Optional.ofNullable(recipeDetails.getFeaturedImage()).ifPresent(image -> {
            String imageId = imageService.saveImage(image).getId();
            recipeBuilder.featuredImageId(imageId);
        });

        recipeBuilder
                .id(UUID.randomUUID().toString())
                .title(recipeDetails.getTitle()).accountId(accountId).createdAt(new Date())
                .description(recipeDetails.getDescription())
                .completionTimeInMinutes(recipeDetails.getCompletionTimeInMinutes())
                .numServings(recipeDetails.getNumServings()).ingredients(recipeDetails.getIngredients())
                .instructions(recipeDetails.getInstructions()).build();

        recipeDao.save(recipeBuilder.build());
    }

    public List<RecipePreview> getLatestRecipes() {
        return recipeDao.getLatestRecipes().stream()
                .map(this::getRecipePreview).toList();
    }

    public RecipeResponse getRecipe(String id) {
        Recipe recipe = recipeDao.getRecipeById(id);
        Profile profile = profileService.getByAccountId(recipe.getAccountId());

        return RecipeResponse.builder()
                .title(recipe.getTitle()).createdAt(recipe.getCreatedAt())
                .authorFirstName(profile.getFirstName()).authorLastName(profile.getLastName())
                .description(recipe.getDescription()).featuredImageId(recipe.getFeaturedImageId())
                .ingredients(recipe.getIngredients()).instructions(recipe.getInstructions())
                .completionTimeInMinutes(recipe.getCompletionTimeInMinutes())
                .numServings(recipe.getNumServings()).build();
    }

    public List<RecipePreview> getRecipesForUser(String username) {
        return recipeDao.getAllByUsername(username);
    }

    public List<RecipePreview> getSavedRecipesForUser(String username) {
        return recipeDao.getSavedByUsername(username).stream()
                .map(this::getRecipePreview).toList();
    }

    public void saveRecipe(SaveRequest saveRequest) {
        Save save = createSave(saveRequest.getRecipeId(), saveRequest.getUsername());
        recipeDao.saveRecipeForAccount(save);
    }

    public void deleteSave(String recipeId, String username) {
        Save save = createSave(recipeId, username);
        recipeDao.deleteSaveForAccount(save);
    }

    private Save createSave(String recipeId, String username) {
        String accountId = accountService.getAccountId(username);
        return Save.builder()
                .accountId(accountId).recipeId(recipeId)
                .createdAt(new Date()).build();
    }

    public Recipe updateRecipe(String id, Map<String, Object> updates) {
        Recipe recipe = recipeDao.getRecipeById(id);
        String imageId;

        List.of("completionTimeInMinutes", "numServings").forEach(field -> {
            if (updates.containsKey(field)) {
                updates.replace(field, Double.parseDouble((String) updates.get(field)));
            }
        });

        if (updates.containsKey("featuredImage")) {
            imageId = imageService.saveImage((MultipartFile) updates.get("featuredImage")).getId();
            updates.remove("featuredImage");
            updates.put("featuredImageId", imageId);
        }

        updates.forEach((key, value) -> {
            Optional.of(ReflectionUtils.findField(Recipe.class, key)).ifPresent(field -> {
                field.setAccessible(true);
                ReflectionUtils.setField(field, recipe, value);
                field.setAccessible(false);
            });
        });
        return recipeDao.update(recipe);
    }

    public void delete(String id) {}

    private RecipePreview getRecipePreview(Recipe recipe) {
        RecipePreview.RecipePreviewBuilder recipePreviewBuilder = RecipePreview.builder();

        Optional.ofNullable(recipe.getFeaturedImageId()).ifPresent(recipePreviewBuilder::featuredImageId);
        Optional.ofNullable(recipe.getDescription()).ifPresent(recipePreviewBuilder::description);
        Optional.ofNullable(recipe.getRating()).ifPresent(recipePreviewBuilder::rating);

        return recipePreviewBuilder
                .id(recipe.getId())
                .title(recipe.getTitle()).createdAt(recipe.getCreatedAt()).build();
    }
}
