package com.recipedb.api.controller;

import com.recipedb.api.dto.LoginDetailsDto;
import com.recipedb.api.model.Profile;
import com.recipedb.api.service.LoginService;
import com.recipedb.api.service.ProfileService;
import com.recipedb.api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDetailsDto loginDetails) {
        try {
            UserDetails userDetails = loginService.login(loginDetails);
            String jwtToken = jwtTokenUtil.generateToken(userDetails);
            Profile profile = profileService.getByUsername(loginDetails.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .body(profile);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
