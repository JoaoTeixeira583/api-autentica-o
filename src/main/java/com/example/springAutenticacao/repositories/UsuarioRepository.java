package com.example.springAutenticacao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springAutenticacao.entities.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario,Long>{   
    // Metodos numa interface são publico já então não precise declarar
    // Para buscar um usuario pelo email
    Optional<Usuario> findByEmail(String email);
} 