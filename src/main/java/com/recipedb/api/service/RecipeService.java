package com.recipedb.api.service;

import com.recipedb.api.dao.AccountDao;
import com.recipedb.api.dao.RecipeDao;
import com.recipedb.api.dto.RecipeDetailsDto;
import com.recipedb.api.model.Account;
import com.recipedb.api.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RecipeDao recipeDao;

    public void create(RecipeDetailsDto recipeDetails) {
        int accountId = getAccountId();
        Recipe recipe = Recipe.builder()
                .title(recipeDetails.getTitle()).accountId(accountId).createdAt(new Date())
                .description(recipeDetails.getDescription())
                .completionTimeInMinutes(recipeDetails.getCompletionTimeInMinutes())
                .numServings(recipeDetails.getNumServings())
                .ingredients(recipeDetails.getIngredients())
                .instructions(recipeDetails.getInstructions()).build();
        recipeDao.save(recipe);
    }

    public void update() {}

    public void get(int id) {}

    public List<Recipe> getAll() {
        return recipeDao.getAllRecipes();
    }

    public void delete(int id) {}

    private Integer getAccountId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDao.findByUsername(userDetails.getUsername())
                .map(Account::getId).orElse(null);
    }
}
