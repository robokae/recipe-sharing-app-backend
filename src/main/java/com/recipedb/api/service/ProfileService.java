package com.recipedb.api.service;

import com.recipedb.api.constants.ErrorMessages;
import com.recipedb.api.dto.ProfileResponse;
import com.recipedb.api.exception.AccountNotFoundException;
import com.recipedb.api.model.Profile;
import com.recipedb.api.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileResponse getByUsername(String username) {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PROFILE_SUMMARY_NOT_FOUND));

        return ProfileResponse.builder()
                .username(username)
                .firstName(profile.getFirstName()).lastName(profile.getLastName())
                .email(profile.getEmail()).description(profile.getDescription())
                .joinDate(profile.getCreatedAt()).build();
    }

    public Profile getByAccountId(String accountId) {
        return profileRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
