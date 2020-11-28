/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.service;

import com.ahmed.jwt.app.dto.UserDto;
import com.ahmed.jwt.app.model.User;
import com.ahmed.jwt.app.repository.UserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 *
 * @author Ahmed Hafez
 */
@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserDto addUser(UserDto userDto) throws ConstraintViolationException {
        try {
            if (userDto.getPassword() != null && !"".equals(userDto.getPassword().trim())) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            User user = convertToEntity(userDto);
            userRepo.save(user);
            userDto.setId(user.getId());
            return userDto;
        } catch (ConstraintViolationException ex) {
            throw new ConstraintViolationException(ex.getConstraintViolations());
        }
    }

    public UserDto updateUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        Optional<User> newUser = this.userRepo.findById(user.getId());
        if (newUser.isPresent()) {
            User updatedUser = newUser.get();
            updatedUser.setId(user.getId());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(updatedUser);
            return userDto;
        } else {
            return userDto;
        }
    }
    public List<UserDto> getAllUser() {
        List<User> allUsers = userRepo.findAll();
        List<UserDto> users = new ArrayList<UserDto>();
        for (User user : allUsers) {
            UserDto userDto = convertToDto(user);
            users.add(userDto);
        }
        return users;
    }

    public UserDto getUserById(long userId) {
        if(userRepo.findById(userId).get()== null){
            throw new NoSuchElementException();
        }
        return convertToDto(userRepo.findById(userId).get());
    }

    public boolean deleteUser(long id) {
        User user = userRepo.findById(id).get();
        if (user != null) {
            userRepo.delete(this.userRepo.findById(id).get());
            return true;
        } else {
            throw new NoSuchElementException();
        }

    }

    public UserDto getUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        return (user == null) ? null : convertToDto(user);
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
