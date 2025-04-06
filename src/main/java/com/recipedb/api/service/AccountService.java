package com.recipedb.api.service;

import com.recipedb.api.dao.Dao;
import com.recipedb.api.model.Account;
import com.recipedb.api.model.RegistrationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private Dao<Account> accountDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Account> getAccount(int id) {
        return accountDao.get(id);
    }

    public void createAccount(RegistrationDetails registrationDetails) {
        String encryptedPassword = passwordEncoder.encode(registrationDetails.getPassword());
        Account account = Account.builder()
                .firstName(registrationDetails.getFirstName()).lastName(registrationDetails.getLastName())
                .email(registrationDetails.getEmail()).username(registrationDetails.getUsername())
                .password(encryptedPassword).role("USER").build();
        accountDao.save(account);
    }
}
