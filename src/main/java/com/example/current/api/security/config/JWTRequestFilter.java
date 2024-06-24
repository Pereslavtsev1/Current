package com.example.current.api.security.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.current.repository.UserRepository;
import com.example.current.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                var username = jwtService.getUsernameFromJwt(token);
                var optionalLocalUser = userRepository.findByUsername(username);
                if (optionalLocalUser.isPresent()) {
                    var user = optionalLocalUser.get();
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException ex){
                ex.printStackTrace();
            }

        }
        filterChain.doFilter(request, response);
    }
}
