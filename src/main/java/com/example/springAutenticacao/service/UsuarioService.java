package com.example.springAutenticacao.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springAutenticacao.entities.Usuario;
import com.example.springAutenticacao.repositories.UsuarioRepository;

@Service 
public class UsuarioService {
    
    //  Para criar um atributo
     private final UsuarioRepository usuarioRepository;
     private final PasswordEncoder passwordEncoder;

     public UsuarioService(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
     }
    // Meotodo para salvar o Usuario

    public Usuario salvarUsuario(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    // Metodo para devolver o usuario pelo email
    // O parâmetro é o dado que o método precisa receber de fora para poder trabalhar.
    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    // Metodo de Login
    public Optional<Usuario> login(String email, String senha){
    // Para procura no banco alguém com esse email 
     Optional<Usuario> usuarioOptional = buscarPorEmail(email);
     
    //  Para saber se o Optional esat prenchido
     if(usuarioOptional.isPresent()){
        // Para ver se tem o usuario
        Usuario usuario = usuarioOptional.get();
        // Aqui ve se tem e retornar true ou false
        boolean resultado = passwordEncoder.matches(senha, usuario.getSenha());
        
        // Faz a comparação do resultadp
        if(resultado == true){
            return usuarioOptional;
        }else{
            return Optional.empty();
        }
     }else{
        return Optional.empty();
     }
    }
}
