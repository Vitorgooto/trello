package com.trello.controller;

import com.trello.dto.TarefaDTO;
import com.trello.model.Tarefa;
import com.trello.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @Autowired
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<TarefaDTO> criarTarefa(@PathVariable Long usuarioId, @RequestBody TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setPrazo(tarefaDTO.getPrazo());
        tarefa.setStatus(tarefaDTO.getStatus());
        
        Tarefa tarefaSalva = tarefaService.criarTarefa(tarefa, usuarioId);
        if (tarefaSalva != null) {
            TarefaDTO responseDTO = new TarefaDTO();
            responseDTO.setId(tarefaSalva.getId());
            responseDTO.setTitulo(tarefaSalva.getTitulo());
            responseDTO.setDescricao(tarefaSalva.getDescricao());
            responseDTO.setDataCriacao(tarefaSalva.getDataCriacao());
            responseDTO.setPrazo(tarefaSalva.getPrazo());
            responseDTO.setStatus(tarefaSalva.getStatus());
            responseDTO.setUsuarioId(tarefaSalva.getUsuario().getId());
            return ResponseEntity.created(URI.create("/api/tarefas/" + tarefaSalva.getId())).body(responseDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TarefaDTO>> listarTarefasPorUsuario(@PathVariable Long usuarioId) {
        List<Tarefa> tarefas = tarefaService.listarTarefasPorUsuario(usuarioId);
        List<TarefaDTO> tarefasDTO = tarefas.stream()
            .map(tarefa -> {
                TarefaDTO dto = new TarefaDTO();
                dto.setId(tarefa.getId());
                dto.setTitulo(tarefa.getTitulo());
                dto.setDescricao(tarefa.getDescricao());
                dto.setDataCriacao(tarefa.getDataCriacao());
                dto.setPrazo(tarefa.getPrazo());
                dto.setStatus(tarefa.getStatus());
                dto.setUsuarioId(tarefa.getUsuario().getId());
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(tarefasDTO);
    }

    @GetMapping("/usuario/{usuarioId}/status/{status}")
    public ResponseEntity<List<TarefaDTO>> listarTarefasPorStatus(
            @PathVariable Long usuarioId,
            @PathVariable Tarefa.StatusTarefa status) {
        List<Tarefa> tarefas = tarefaService.listarTarefasPorStatus(usuarioId, status);
        List<TarefaDTO> tarefasDTO = tarefas.stream()
            .map(tarefa -> {
                TarefaDTO dto = new TarefaDTO();
                dto.setId(tarefa.getId());
                dto.setTitulo(tarefa.getTitulo());
                dto.setDescricao(tarefa.getDescricao());
                dto.setDataCriacao(tarefa.getDataCriacao());
                dto.setPrazo(tarefa.getPrazo());
                dto.setStatus(tarefa.getStatus());
                dto.setUsuarioId(tarefa.getUsuario().getId());
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(tarefasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
            .map(tarefa -> {
                TarefaDTO dto = new TarefaDTO();
                dto.setId(tarefa.getId());
                dto.setTitulo(tarefa.getTitulo());
                dto.setDescricao(tarefa.getDescricao());
                dto.setDataCriacao(tarefa.getDataCriacao());
                dto.setPrazo(tarefa.getPrazo());
                dto.setStatus(tarefa.getStatus());
                dto.setUsuarioId(tarefa.getUsuario().getId());
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setPrazo(tarefaDTO.getPrazo());
        tarefa.setStatus(tarefaDTO.getStatus());
        
        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefa);
        if (tarefaAtualizada != null) {
            TarefaDTO responseDTO = new TarefaDTO();
            responseDTO.setId(tarefaAtualizada.getId());
            responseDTO.setTitulo(tarefaAtualizada.getTitulo());
            responseDTO.setDescricao(tarefaAtualizada.getDescricao());
            responseDTO.setDataCriacao(tarefaAtualizada.getDataCriacao());
            responseDTO.setPrazo(tarefaAtualizada.getPrazo());
            responseDTO.setStatus(tarefaAtualizada.getStatus());
            responseDTO.setUsuarioId(tarefaAtualizada.getUsuario().getId());
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        if (tarefaService.deletarTarefa(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 