package com.recipedb.api.security;

import com.recipedb.api.dao.AccountDao;
import com.recipedb.api.model.Account;
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
    private AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountDao.findByUsername(username);
        return new User(account.getUsername(), account.getPassword(),
                List.of(new SimpleGrantedAuthority(account.getRole())));
    }
}
