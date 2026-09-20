package com.example.springAutenticacao.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
// Herda o comportamento de OncePerRequetsFilter
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil){
         this.jwtUtil = jwtUtil;
    }

    @Override
    // Override avisa o java esta substituindo um método que já existe na classe pai
    // HttpServletResponse response representa a reposta que vai ser enviada de volta pro cliente
    // FilterChain representa a fila de filtros que falta a chegar ao controler
    // throws ServletException, IOException decalra o metodo pode lançar dois tipos de erro

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            try {
                String email= jwtUtil.extrairEmail(token);
                //  SPring Security entende uma autenticação válida
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null,null);
                // Tipo um carimbo uma requisiçao autal como autenticada
                SecurityContextHolder.getContext()
                     .setAuthentication(authToken);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
