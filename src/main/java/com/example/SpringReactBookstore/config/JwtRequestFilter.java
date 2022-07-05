package com.example.SpringReactBookstore.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenWithBearer = request.getHeader("Authorization");

        if(tokenWithBearer == null || !tokenWithBearer.startsWith("Beer")){

            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenWithBearer.substring(5); // remove "Bearer " from beginning of token string

        Authentication authentication = jwtUtil.validateToken(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
