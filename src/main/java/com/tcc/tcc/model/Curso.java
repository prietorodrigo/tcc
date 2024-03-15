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
    private List<Proposta> propostas = new ArrayList<>();

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

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }
}
