/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Ahmed Hafez
 */

public class NotFoundException  extends ApiBaseException{

    public NotFoundException(String message) {
        super(message);
    }
    @Override
    public HttpStatus getStatusCode(){
        return HttpStatus.NOT_FOUND;
    }
    
}
