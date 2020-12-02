/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.security;

import com.ahmed.jwt.app.dto.UserDto;
import com.ahmed.jwt.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ahmed Hafez
 */
@Service
public class AppUserService implements UserDetailsService{
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("This Username Not Found");
        }
        return new AppUserDetails(user);
    } 
    
}
