package com.trello.repository;

import com.trello.model.Tarefa;
import com.trello.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuario(Usuario usuario);
    List<Tarefa> findByUsuarioAndStatus(Usuario usuario, Tarefa.StatusTarefa status);
} 