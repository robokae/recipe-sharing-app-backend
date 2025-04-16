package com.recipedb.api.service;

import com.recipedb.api.dao.AccountDao;
import com.recipedb.api.dto.LoginDetailsDto;
import com.recipedb.api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDetails login(LoginDetailsDto loginDetails) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDetails.getUsername(), loginDetails.getPassword()));
        return (User) authentication.getPrincipal();
    }
}
