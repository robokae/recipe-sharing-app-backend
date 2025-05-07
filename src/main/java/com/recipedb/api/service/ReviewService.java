package com.recipedb.api.service;

import com.recipedb.api.dao.ReviewDao;
import com.recipedb.api.dto.ReviewRequest;
import com.recipedb.api.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ReviewDao reviewDao;

    public List<Review> getAllForRecipe(String recipeId) {
        return reviewDao.findAllByRecipeId(recipeId);
    }

    public Review create(ReviewRequest reviewRequest) {
        String accountId = accountService.getAccountId(reviewRequest.getUsername());
        Review review = Review.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .accountId(accountId).recipeId(reviewRequest.getRecipeId())
                .score(reviewRequest.getRating())
                .description(reviewRequest.getDescription()).build();
        return reviewDao.save(review);
    }
}
