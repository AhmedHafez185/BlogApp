/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.security.jwt;

import com.ahmed.jwt.app.security.AppUserDetails;
import com.ahmed.jwt.app.security.AppUserService;
import com.ahmed.jwt.app.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author HP
 */
public class AuthFilter extends OncePerRequestFilter{
    
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserService userService;
    @Autowired
    AppUserService appUserService;
@Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(SecurityConstants.TOKEN_HEADER);
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        if (header != null && securityContext.getAuthentication() == null) {
        	System.out.println(header);
            String token = header.substring("Bearer ".length());
            String username = tokenUtil.getUsernameFromToken(token);
            if(username != null) {
            
                UserDetails userDetails=appUserService.loadUserByUsername(username);
                if (tokenUtil.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }


        filterChain.doFilter(request, response);
    }
}
