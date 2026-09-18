package com.example.springAutenticacao.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component 
public class JwtUtil {
    // Para pegar o valor do application.properties numa variáve
    @Value("${jwt.secret}")
    private String chaveSecreta;

    // Metodo para transforma chaveSecreta um objeto SecretKey
    private SecretKey getSecretKey(){
       byte[] bytes= chaveSecreta.getBytes();
       return Keys.hmacShaKeyFor(bytes);
    }

    // Metodo para gerar token
    public String gerarToken(String email){
        Date agora = new Date();
        long tempoExpiracao = System.currentTimeMillis()+ 1000 * 60 * 60;

         return  Jwts.builder()
               .subject(email) //dono do token
               .issuedAt(agora) // quando foi criado
               .expiration(new Date(tempoExpiracao)) // qunado expira
               .signWith(getSecretKey()) //assina com sua chave
               .compact(); // gera a String final
    }
}
