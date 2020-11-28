/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.controller;
import com.ahmed.jwt.app.dto.Person;
import com.ahmed.jwt.app.dto.UserDto;
import com.ahmed.jwt.app.exception.ConflictException;
import com.ahmed.jwt.app.exception.CustomValidationException;
import com.ahmed.jwt.app.exception.NotFoundException;
import com.ahmed.jwt.app.service.UserService;
import com.ahmed.jwt.app.validation.group.Group1;
import com.ahmed.jwt.app.validation.group.Group2;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ahmed Hafez
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    Validator validator;
    @Autowired
    UserService userService;
    //  @CrossOrigin(origins = "*", allowedHeaders = "*")

    @GetMapping()
    public List<UserDto> getAllUser() {
        return userService.getAllUser();
    }

    // @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) throws NotFoundException {
        try {
            return userService.getUserById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("Record Not Found With Id  [" + id + "]"));
        }

    }

    // @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto, Errors errors) throws ConflictException, CustomValidationException, ConstraintViolationException, Exception {
        if (errors.hasErrors()) {
            throw new CustomValidationException(errors);
        }
        if (userService.getUserByUsername(userDto.getUsername()) != null) {
            throw new ConflictException("This username is exists , choose another one!");
        }
        try {
            UserDto user = userService.addUser(userDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (ConstraintViolationException ex) {
            throw new ConstraintViolationException(ex.getConstraintViolations());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    // @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserDto userDto, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new CustomValidationException(errors);
        }
        if (userService.getUserByUsername(userDto.getUsername()) != null) {
            throw new ConflictException("This username is exists , choose another one!");
        }
        try {
            userService.getUserById(id);
            userDto.setId(id);
            UserDto updatedUser = userService.updateUser(userDto);
            return updatedUser;
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (ConstraintViolationException ex) {
            throw new ConstraintViolationException(ex.getConstraintViolations());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

    //   @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable long id) throws Exception {
        boolean status = false;
        try {
            status = this.userService.deleteUser(id);
        }catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("Record Not Found With Id  [" + id + "]"));
        } 
        catch (Exception ex) {
            throw new Exception(String.format("Error in Deleting this Record , "+ex.getLocalizedMessage()));
        }
        return status;
    }

    ///////////////////// Test Grouping /////////////////
    @PostMapping("/group1")
    public ResponseEntity<Person> testGroup1(@Valid @RequestBody Person person, Errors errors) throws ConflictException, CustomValidationException, ConstraintViolationException {
        if (errors.hasErrors()) {
            System.out.println("Ahmed Alaa From Errors Here ");
            throw new CustomValidationException(errors);
        }
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        constraintViolations = validator.validate(person, Group1.class);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/group2")
    public ResponseEntity<Person> testGroup2(@Valid @RequestBody Person person, Errors errors) throws ConflictException, CustomValidationException, ConstraintViolationException {
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        constraintViolations = validator.validate(person, Group2.class);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
