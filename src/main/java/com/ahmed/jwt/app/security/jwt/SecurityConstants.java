/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.security.jwt;

/**
 *
 * @author HP
 */
public interface SecurityConstants {

    public static final String CLAIMS_SUBJECT = "sub";
    public static final String CLAIMS_CREATED = "created";
    public static final Long   TOKEN_VALIDITY = 7604800L;// 7 days
    public static final String TOKEN_SECRET = "Ahmmed_JWT_App";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
