package com.travelbnb.config;

import com.travelbnb.entity.AppUser;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public JWTRequestFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            String username = jwtService.getUserName(token);
            Optional<AppUser> opUsername = appUserRepository.findByUsername(username);
            if(opUsername.isPresent()) {
                AppUser appUser = opUsername.get();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(appUser,null, Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
