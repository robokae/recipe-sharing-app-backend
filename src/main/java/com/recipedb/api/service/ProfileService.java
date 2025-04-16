package com.recipedb.api.service;

import com.recipedb.api.dao.ProfileDao;
import com.recipedb.api.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileDao profileDao;

    public Profile getByUsername(String username) {
        return profileDao.findByAccountId(username);
    }

    public Profile createProfile(Profile profile) {
        return profileDao.save(profile);
    }
}
