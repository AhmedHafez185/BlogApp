/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ahmed Hafez
 */
public class UserDto {
    
    private Long id;
    @NotNull(message = "First Name Must not Null")
    @Size(min=3, message = "First Name Length Must be more than 3 characters")
    private String firstName;
    @NotNull(message = "Last Name Must not NUll")
    @Size(min=3, message = "Last Name Length Must be more than 3 characters")
    private String lastName;
    @NotNull(message = "username is required")
    @Size(min=3,message = "username length more than 3 characters ")
    private String username;
    @NotNull(message = "Password is required")
    private String password;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
