package com.recipedb.api.security;

import com.recipedb.api.constants.ErrorMessages;
import com.recipedb.api.exception.AccountNotFoundException;
import com.recipedb.api.model.Account;
import com.recipedb.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));
        return new User(account.getUsername(), account.getPassword(),
                List.of(new SimpleGrantedAuthority(account.getRole())));
    }
}
