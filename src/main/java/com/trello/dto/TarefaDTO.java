package com.trello.dto;

import com.trello.model.Tarefa;
import java.time.LocalDateTime;

public class TarefaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime prazo;
    private Tarefa.StatusTarefa status;
    private Long usuarioId;

    public TarefaDTO() {
    }

    public TarefaDTO(Long id, String titulo, String descricao, LocalDateTime dataCriacao, 
                     LocalDateTime prazo, Tarefa.StatusTarefa status, Long usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.prazo = prazo;
        this.status = status;
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDateTime prazo) {
        this.prazo = prazo;
    }

    public Tarefa.StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(Tarefa.StatusTarefa status) {
        this.status = status;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
} 