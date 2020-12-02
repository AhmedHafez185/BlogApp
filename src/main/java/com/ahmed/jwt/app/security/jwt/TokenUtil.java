/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ahmed Hafez
 */
@Component
public class TokenUtil {

    public String generateToken(UserDetails userDetails) {
        //1-claims 2-ExpirationDate 3-sign  4-compact
        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.CLAIMS_SUBJECT, userDetails.getUsername());
        claims.put(SecurityConstants.CLAIMS_CREATED, new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_VALIDITY * 1000);
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            String username = claims.getSubject();
            return username;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException ex) {
            return null;

        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return (expiration.before(new Date()));
    }

    private Claims getClaims(String token) {

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET)
                    .parseClaimsJwt(token)
                    .getBody();
            return claims;
        } catch (Exception ex) {
            claims = null;
        }
        return claims;
    }

}
