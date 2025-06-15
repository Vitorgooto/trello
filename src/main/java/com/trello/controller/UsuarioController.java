package com.trello.controller;

import com.trello.dto.UsuarioDTO;
import com.trello.model.Usuario;
import com.trello.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        
        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);
        
        UsuarioDTO responseDTO = new UsuarioDTO();
        responseDTO.setId(usuarioSalvo.getId());
        responseDTO.setNome(usuarioSalvo.getNome());
        responseDTO.setEmail(usuarioSalvo.getEmail());
        
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioSalvo.getId())).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(usuario -> {
                UsuarioDTO dto = new UsuarioDTO();
                dto.setId(usuario.getId());
                dto.setNome(usuario.getNome());
                dto.setEmail(usuario.getEmail());
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
            .map(usuario -> {
                UsuarioDTO dto = new UsuarioDTO();
                dto.setId(usuario.getId());
                dto.setNome(usuario.getNome());
                dto.setEmail(usuario.getEmail());
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        if (usuarioAtualizado != null) {
            UsuarioDTO responseDTO = new UsuarioDTO();
            responseDTO.setId(usuarioAtualizado.getId());
            responseDTO.setNome(usuarioAtualizado.getNome());
            responseDTO.setEmail(usuarioAtualizado.getEmail());
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (usuarioService.deletarUsuario(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 