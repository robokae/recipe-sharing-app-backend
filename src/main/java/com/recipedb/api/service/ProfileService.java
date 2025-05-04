package com.recipedb.api.service;

import com.recipedb.api.dao.ProfileDao;
import com.recipedb.api.dto.ProfileResponse;
import com.recipedb.api.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileDao profileDao;

    public ProfileResponse getByUsername(String username) {
        Profile profile = profileDao.findByUsername(username);
        return ProfileResponse.builder()
                .username(username).firstName(profile.getFirstName()).lastName(profile.getLastName())
                .email(profile.getEmail()).description(profile.getDescription()).createdAt(profile.getCreatedAt())
                .build();
    }

    public Profile getByAccountId(String accountId) {
        return profileDao.findByAccountId(accountId);
    }

    public Profile createProfile(Profile profile) {
        return profileDao.save(profile);
    }
}
