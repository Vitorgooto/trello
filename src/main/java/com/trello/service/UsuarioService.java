package com.trello.service;

import com.trello.model.Usuario;
import com.trello.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {
        System.out.println("Criando usuário - Nome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Senha: " + usuario.getSenha());
        
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        
        System.out.println("Usuário salvo - ID: " + usuarioSalvo.getId());
        System.out.println("Senha salva: " + usuarioSalvo.getSenha());
        
        return usuarioSalvo;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            System.out.println("Usuário encontrado por email - ID: " + usuario.get().getId());
            System.out.println("Senha: " + usuario.get().getSenha());
        } else {
            System.out.println("Nenhum usuário encontrado com o email: " + email);
        }
        return usuario;
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuario.setSenha(usuarioAtualizado.getSenha());
            }
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public boolean deletarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 