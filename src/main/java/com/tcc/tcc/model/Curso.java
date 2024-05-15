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

    @NotBlank
    private String abreviatura;

    @NotBlank
    private String disciplinastcc;

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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDisciplinastcc() {
        return disciplinastcc;
    }

    public void setDisciplinastcc(String disciplinastcc) {
        this.disciplinastcc = disciplinastcc;
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
