package com.example.current.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.current.repository.UserRepository;
import com.example.current.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter implements ChannelInterceptor {


    private final JWTService jwtService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken token = checkToken(header);
        if (token != null) {
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
        filterChain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken checkToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                var username = jwtService.getUsernameFromJwt(token);
                var optionalLocalUser = userRepository.findByUsername(username);
                if (optionalLocalUser.isPresent()) {
                    var user = optionalLocalUser.get();
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return authentication;
                }
            } catch (JWTDecodeException ex){
                ex.printStackTrace();
            }
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return null;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        var nativeHeaders = (Map) message.getHeaders().get("nativeHeaders");
        if (nativeHeaders != null) {
            var auth = (List) nativeHeaders.get("Authorization");
            if (auth != null) {
                String tokenHeader = auth.get(0).toString();
                checkToken(tokenHeader);
            }
        }
        return message;
    }
}
