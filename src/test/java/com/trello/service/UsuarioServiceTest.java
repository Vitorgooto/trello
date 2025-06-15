package com.trello.service;

import com.trello.model.Usuario;
import com.trello.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Teste");
        usuario.setEmail("teste@email.com");
        usuario.setSenha("senha123");
    }

    @Test
    void criarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioSalvo = usuarioService.criarUsuario(new Usuario());

        assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void buscarPorEmail() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        Optional<Usuario> foundUsuario = usuarioService.buscarPorEmail("teste@email.com");

        assertEquals(usuario.getEmail(), foundUsuario.get().getEmail());
        verify(usuarioRepository, times(1)).findByEmail(anyString());
    }
} 