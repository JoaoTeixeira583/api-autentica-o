package com.example.springAutenticacao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springAutenticacao.dto.LoginDto;
import com.example.springAutenticacao.entities.Usuario;
import com.example.springAutenticacao.service.UsuarioService;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/auth")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
         this.usuarioService = usuarioService;
    }
    
    // Para registrar o usuario
    @PostMapping("/register")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
       return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUsuario(@RequestBody LoginDto loginDto){
      Optional<Usuario> login = usuarioService.login(loginDto.getEmail(), loginDto.getSenha());

      if(login.isPresent()){
        Usuario usuario = login.get();
          return ResponseEntity.ok(usuario);
      }else{
        return ResponseEntity.status(401).build();
      }
    }
    
}
