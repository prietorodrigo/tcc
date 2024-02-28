package com.tcc.tcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="producaocientifica")
public class ProducaoCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private int semestre;

    @NotBlank
    private int ano;

    @NotBlank
    @Lob
    private String resumo;

    private String pdf;

    @Lob
    private String apresentacoes;

    @NotBlank
    @Lob
    private String palavrasChaves;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="prodcientifica_proposta",
            joinColumns={@JoinColumn(name="prodcientifica_id", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="proposta_id", referencedColumnName="ID")})
    private List<Proposta> proposta = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="prodcientifica_curso",
            joinColumns={@JoinColumn(name="prodcientifica_id", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="curso_id", referencedColumnName="ID")})
    private List<Curso> curso = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getApresentacoes() {
        return apresentacoes;
    }

    public void setApresentacoes(String apresentacoes) {
        this.apresentacoes = apresentacoes;
    }

    public String getPalavrasChaves() {
        return palavrasChaves;
    }

    public void setPalavrasChaves(String palavrasChaves) {
        this.palavrasChaves = palavrasChaves;
    }

    public List<Proposta> getProposta() {
        return proposta;
    }

    public void setPropostas(List<Proposta> proposta) {
        this.proposta = proposta;
    }

    public List<Curso> getCurso() {
        return curso;
    }

    public void setCursos(List<Curso> curso) {
        this.curso = curso;
    }
}
