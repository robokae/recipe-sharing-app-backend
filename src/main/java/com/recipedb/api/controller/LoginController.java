package com.recipedb.api.controller;

import com.recipedb.api.dto.LoginRequest;
import com.recipedb.api.dto.ProfileResponse;
import com.recipedb.api.service.LoginService;
import com.recipedb.api.service.ProfileService;
import com.recipedb.api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ProfileResponse> login(@RequestBody LoginRequest loginDetails) {
        UserDetails userDetails = loginService.login(loginDetails);
        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body(profileService.getByUsername(loginDetails.getUsername()));
    }
}
