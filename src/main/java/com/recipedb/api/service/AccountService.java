package com.recipedb.api.service;

import com.recipedb.api.constants.ErrorMessages;
import com.recipedb.api.exception.AccountNotFoundException;
import com.recipedb.api.repository.AccountRepository;
import com.recipedb.api.exception.UsernameAlreadyExistException;
import com.recipedb.api.model.Account;
import com.recipedb.api.dto.RegisterRequest;
import com.recipedb.api.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Profile createAccount(RegisterRequest registerDetails) {

        accountRepository.findByUsername(registerDetails.getUsername()).ifPresent(account -> {
            throw new UsernameAlreadyExistException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        });

        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .username(registerDetails.getUsername())
                .password(passwordEncoder.encode(registerDetails.getPassword()))
                .role("USER").build();

        Account savedAccount = accountRepository.save(account);

        Profile profile = Profile.builder()
                .accountId(savedAccount.getId())
                .firstName(registerDetails.getFirstName()).lastName(registerDetails.getLastName())
                .email(registerDetails.getEmail())
                .createdAt(new Date()).build();

        return profileService.createProfile(profile);
    }

    public String getAccountId(String username) {
        return accountRepository.findByUsername(username)
                .map(Account::getId)
                .orElseThrow(() -> new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));
    }
}
