package com.recipedb.api.service;

import com.recipedb.api.dao.AccountDao;
import com.recipedb.api.dao.ProfileDao;
import com.recipedb.api.exception.UserAlreadyExistException;
import com.recipedb.api.model.Account;
import com.recipedb.api.dto.RegisterDetailsDto;
import com.recipedb.api.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Profile createAccount(RegisterDetailsDto registerDetails) throws UserAlreadyExistException {
        accountDao.findByUsername(registerDetails.getUsername()).ifPresent(account -> {
            throw new UserAlreadyExistException("User already exists");
        });

        String encryptedPassword = passwordEncoder.encode(registerDetails.getPassword());

        Account savedAccount = accountDao.save(Account.builder()
                .username(registerDetails.getUsername())
                .password(encryptedPassword)
                .role("USER").build());

        return profileService.createProfile(Profile.builder()
                .firstName(registerDetails.getFirstName()).lastName(registerDetails.getLastName())
                .accountId(savedAccount.getId()).email(registerDetails.getEmail())
                .createdAt(new Date()).build());
    }
}
