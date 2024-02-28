package com.tcc.tcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "curso")
    private List<UsuarioCurso> usuarioCursos = new ArrayList<>();

    @OneToMany(mappedBy = "curso")
    private List<TermoCompromisso> termosCompromisso = new ArrayList<>();

    @ManyToMany(mappedBy="curso")
    private List<ProducaoCientifica> producaoCientificas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<UsuarioCurso> getUsuarioCursos() {
        return usuarioCursos;
    }

    public void setUsuarioCursos(List<UsuarioCurso> usuarioCursos) {
        this.usuarioCursos = usuarioCursos;
    }

    public List<TermoCompromisso> getTermosCompromisso() {
        return termosCompromisso;
    }

    public void setTermosCompromisso(List<TermoCompromisso> termosCompromisso) {
        this.termosCompromisso = termosCompromisso;
    }

    public List<ProducaoCientifica> getProducaoCientificas() {
        return producaoCientificas;
    }

    public void setProducaoCientificas(List<ProducaoCientifica> producaoCientificas) {
        this.producaoCientificas = producaoCientificas;
    }
}
