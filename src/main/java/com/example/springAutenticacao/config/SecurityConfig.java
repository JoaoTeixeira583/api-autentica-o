package com.example.springAutenticacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration 
public class SecurityConfig {
    
    @Bean 
    // Bean pega o objeto retornado e vira gerenciado por spring
    // SecurityFilterChain a sequência de filtros que decide como uma requisição será tratada pela segurança
    // HttpSecurity é a ferramenta que usamos para montar/configurar as regras de segurança.
    public SecurityFilterChain filtrarRequisicao(HttpSecurity httpSecurity){
        //  Metodo para liberar a permissão da url
        httpSecurity.authorizeHttpRequests(auth ->
            // Para mostrar que so essa url tem essa autenticação
            auth.requestMatchers("/auth/register","/auth/login").permitAll().anyRequest().authenticated()          
         );

         httpSecurity.csrf(csrf ->
            csrf.disable()
         );
        return httpSecurity.build();
    }

    // Esse método vai devolver um objeto capaz de criptografar/hash de senhas.
    // PasswordEnconder é uma interface do Spring Security. Ela define operações para trabalhar com senhas.
    // Crie esse PasswordEncoder e deixe ele disponível para outras classes da aplicação.
    @Bean 
    public PasswordEncoder passwordEncoder(){
        // Para criar uma implentação concreta dessa interface
        return new BCryptPasswordEncoder();
    }
}
