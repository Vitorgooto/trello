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
        Usuario usuario = usuarioService.buscarPorEmail(authDTO.getEmail())
                .orElse(null);

        if (usuario != null && usuario.getSenha().equals(authDTO.getSenha())) {
            String token = jwtTokenService.generateToken(usuario.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Credenciais inválidas");
    }
} 