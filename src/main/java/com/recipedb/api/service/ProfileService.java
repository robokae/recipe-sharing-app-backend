package com.recipedb.api.service;

import com.recipedb.api.dao.ProfileDao;
import com.recipedb.api.model.Profile;
import com.recipedb.api.model.ProfileSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileDao profileDao;

    public ProfileSummary getByUsername(String username) {
        return profileDao.findSummaryByUsername(username);
    }

    public Profile getByAccountId(String accountId) {
        return profileDao.findByAccountId(accountId);
    }

    public Profile createProfile(Profile profile) {
        return profileDao.save(profile);
    }
}
