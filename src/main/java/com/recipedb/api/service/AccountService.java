package com.recipedb.api.service;

import com.recipedb.api.dao.AccountDao;
import com.recipedb.api.exception.UserAlreadyExistException;
import com.recipedb.api.model.Account;
import com.recipedb.api.dto.RegisterDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccount(RegisterDetailsDto registrationDetails) throws UserAlreadyExistException {
        accountDao.findByUsername(registrationDetails.getUsername()).ifPresent(account -> {
            throw new UserAlreadyExistException("User already exists");
        });

        String encryptedPassword = passwordEncoder.encode(registrationDetails.getPassword());
        Account account = Account.builder()
                .firstName(registrationDetails.getFirstName()).lastName(registrationDetails.getLastName())
                .email(registrationDetails.getEmail()).username(registrationDetails.getUsername())
                .password(encryptedPassword).role("USER").build();
        accountDao.save(account);
    }
}
