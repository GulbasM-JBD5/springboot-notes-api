package com.example.thirdyear.security;
import com.example.thirdyear.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter   extends OncePerRequestFilter{
    private final JwtService jwtService;
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    //avtomatik cagrilir
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("AUTH HEADER: [" + authHeader + "]");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7).trim();
            System.out.println("TOKEN FOUND");
            if (jwtService.validateToken(token)) {
                System.out.println("TOKEN VALID");
                String email = jwtService.extractEmail(token);

                System.out.println("EMAIL FROM TOKEN: " + email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                null
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);


    }
}
