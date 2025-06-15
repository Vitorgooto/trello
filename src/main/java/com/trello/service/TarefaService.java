package com.trello.service;

import com.trello.model.Tarefa;
import com.trello.model.Usuario;
import com.trello.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository, UsuarioService usuarioService) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioService = usuarioService;
    }

    public Tarefa criarTarefa(Tarefa tarefa, Long usuarioId) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        if (usuario.isPresent()) {
            tarefa.setUsuario(usuario.get());
            return tarefaRepository.save(tarefa);
        }
        return null;
    }

    public List<Tarefa> listarTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarTarefasPorUsuario(Long usuarioId) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        return usuario.map(tarefaRepository::findByUsuario).orElse(List.of());
    }

    public List<Tarefa> listarTarefasPorStatus(Long usuarioId, Tarefa.StatusTarefa status) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        return usuario.map(u -> tarefaRepository.findByUsuarioAndStatus(u, status)).orElse(List.of());
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setTitulo(tarefaAtualizada.getTitulo());
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
            tarefa.setPrazo(tarefaAtualizada.getPrazo());
            tarefa.setStatus(tarefaAtualizada.getStatus());
            return tarefaRepository.save(tarefa);
        }
        return null;
    }

    public boolean deletarTarefa(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean verificarPropriedade(Long tarefaId, Long usuarioId) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(tarefaId);
        return tarefa.map(t -> t.getUsuario().getId().equals(usuarioId)).orElse(false);
    }
} 