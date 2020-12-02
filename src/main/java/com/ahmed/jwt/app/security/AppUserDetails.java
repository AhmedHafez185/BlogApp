/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.security;

import com.ahmed.jwt.app.dto.UserDto;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *
 * @author Ahmed Hafez
 */
public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private UserDto user;

    public AppUserDetails(UserDto user) {
        this.user = user;
    }

    public AppUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authorty = new GrantedAuthority() {
            private static final long serialVersionUID = 1L;
            @Override
            public String getAuthority() {
                return user.getUsername();
            }
        };
        return Arrays.asList(authorty);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public UserDto getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
