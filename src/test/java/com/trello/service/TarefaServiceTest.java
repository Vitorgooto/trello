package com.trello.service;

import com.trello.model.Tarefa;
import com.trello.model.Usuario;
import com.trello.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private TarefaService tarefaService;

    private Usuario usuario;
    private Tarefa tarefa1;
    private Tarefa tarefa2;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuario Teste");
        usuario.setEmail("usuario@teste.com");
        usuario.setSenha("senha123");

        tarefa1 = new Tarefa();
        tarefa1.setId(1L);
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setDescricao("Descrição da Tarefa 1");
        tarefa1.setPrazo(LocalDateTime.now().plusDays(5));
        tarefa1.setStatus(Tarefa.StatusTarefa.PENDENTE);
        tarefa1.setUsuario(usuario);

        tarefa2 = new Tarefa();
        tarefa2.setId(2L);
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setDescricao("Descrição da Tarefa 2");
        tarefa2.setPrazo(LocalDateTime.now().plusDays(10));
        tarefa2.setStatus(Tarefa.StatusTarefa.CONCLUIDA);
        tarefa2.setUsuario(usuario);
    }

    @Test
    void criarTarefa() {
        when(usuarioService.buscarPorId(anyLong())).thenReturn(Optional.of(usuario));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa1);

        Tarefa tarefaSalva = tarefaService.criarTarefa(new Tarefa(), 1L);

        assertNotNull(tarefaSalva);
        assertEquals(tarefa1.getTitulo(), tarefaSalva.getTitulo());
        verify(usuarioService, times(1)).buscarPorId(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void listarTodasTarefas() {
        when(tarefaRepository.findAll()).thenReturn(Arrays.asList(tarefa1, tarefa2));

        List<Tarefa> tarefas = tarefaService.listarTodasTarefas();

        assertEquals(2, tarefas.size());
        assertEquals(tarefa1.getTitulo(), tarefas.get(0).getTitulo());
        assertEquals(tarefa2.getTitulo(), tarefas.get(1).getTitulo());
        verify(tarefaRepository, times(1)).findAll();
    }
} 