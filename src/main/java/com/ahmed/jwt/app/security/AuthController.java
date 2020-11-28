/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.security;

import com.ahmed.jwt.app.dto.UserDto;
import com.ahmed.jwt.app.security.jwt.JWTResponse;
import com.ahmed.jwt.app.security.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ahmed Hafez
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    TokenUtil tokenUtils;
    @Autowired
    UserDetailsService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = {"","/"})
    public JWTResponse signIn(@RequestBody UserDto user) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = tokenUtils.generateToken(userDetails);
        JWTResponse response = new JWTResponse(token);
        return response;
    }

}
