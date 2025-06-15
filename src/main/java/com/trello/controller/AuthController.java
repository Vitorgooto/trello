package com.trello.controller;

import com.trello.dto.AuthDTO;
import com.trello.model.Usuario;
import com.trello.security.JwtTokenService;
import com.trello.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        System.out.println("Tentativa de login - Email: " + authDTO.getEmail());
        
        if (authDTO.getSenha() == null || authDTO.getSenha().trim().isEmpty()) {
            System.out.println("Senha vazia ou nula");
            return ResponseEntity.badRequest().body("A senha é obrigatória");
        }

        Usuario usuario = usuarioService.buscarPorEmail(authDTO.getEmail())
                .orElse(null);

        if (usuario == null) {
            System.out.println("Usuário não encontrado");
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }

        System.out.println("Usuário encontrado - Senha do usuário: " + usuario.getSenha());
        System.out.println("Senha fornecida: " + authDTO.getSenha());

        if (usuario.getSenha() != null && usuario.getSenha().equals(authDTO.getSenha())) {
            String token = jwtTokenService.generateToken(usuario.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            System.out.println("Login bem sucedido - Token gerado");
            return ResponseEntity.ok(response);
        }

        System.out.println("Senha incorreta");
        return ResponseEntity.badRequest().body("Credenciais inválidas");
    }
} 