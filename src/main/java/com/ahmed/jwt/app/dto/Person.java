/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.dto;

import com.ahmed.jwt.app.validation.group.Group1;
import com.ahmed.jwt.app.validation.group.Group2;
import javax.validation.constraints.Min;
import javax.validation.constraints.Min.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ahmed Hafez
 */
public class Person {
   @Min.List({@Min(value = 1,message = "Group1 error < 1", groups = Group1.class),@Min(value = 5, message = "Group1 error < 5" ,groups = Group2.class)})
    private int id;
    @NotNull(message = "FirstName can't be  null , it Required",groups = Group1.class)
    private String firstName;
    @NotNull(message = "LastName  can't be  null , it Required",groups = Group2.class)
    private String lastName;
    @Min(value = 18, message = "You have to be 18 to drive a car",groups = Group2.class)
    private int age;
    @NotNull(message = "Address can't be  null , it Required",groups = {Group2.class,Group1.class})
    private String address;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, int age, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
