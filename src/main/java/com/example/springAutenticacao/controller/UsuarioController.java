package com.example.springAutenticacao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springAutenticacao.dto.LoginDto;
import com.example.springAutenticacao.dto.LoginResponseDto;
import com.example.springAutenticacao.entities.Usuario;
import com.example.springAutenticacao.security.JwtUtil;
import com.example.springAutenticacao.service.UsuarioService;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/auth")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService,JwtUtil jwtUtil){
         this.usuarioService = usuarioService;
         this.jwtUtil =jwtUtil;
    }
    
    // Para registrar o usuario
    @PostMapping("/register")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
       return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUsuario(@RequestBody LoginDto loginDto){
      Optional<Usuario> login = usuarioService.login(loginDto.getEmail(), loginDto.getSenha());

      if(login.isPresent()){
        Usuario usuario = login.get();

        // Para transformar em token
        String token = jwtUtil.gerarToken(usuario.getEmail());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        
        // Monta o objeto de resposta, juntando 3 informações: nome e email do usuário (vindos do banco), e o token recém-gerado.
        loginResponseDto.setNome(usuario.getNome());
        loginResponseDto.setEmail(usuario.getEmail());
        loginResponseDto.setToken(token);

        return ResponseEntity.ok(loginResponseDto);

      }else{
        return ResponseEntity.status(401).build();
      }
    }
    
}
