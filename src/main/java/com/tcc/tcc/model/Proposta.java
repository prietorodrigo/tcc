package com.tcc.tcc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="proposta")
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data;

    @NotBlank
    private String tema;

    @NotBlank
    @Lob
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToOne(mappedBy="proposta", cascade = CascadeType.ALL)
    private ProducaoCientifica producaoCientifica;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "estudante_id")
    private User estudante;

    @ManyToOne
    @JoinColumn(name = "orientador_id")
    private User orientador;

    private boolean aceitada;

    private boolean cadastrada;

    @OneToOne
    @JoinColumn(name="ideia_id")
    private Ideia ideia;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public ProducaoCientifica getProducaoCientifica() {
        return producaoCientifica;
    }

    public void setProducaoCientifica(ProducaoCientifica producaoCientifica) {
        this.producaoCientifica = producaoCientifica;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public User getEstudante() {
        return estudante;
    }

    public void setEstudante(User estudante) {
        this.estudante = estudante;
    }

    public User getOrientador() {
        return orientador;
    }

    public void setOrientador(User orientador) {
        this.orientador = orientador;
    }

    public boolean isAceitada() {
        return aceitada;
    }

    public void setAceitada(boolean aceitada) {
        this.aceitada = aceitada;
    }

    public boolean isCadastrada() {
        return cadastrada;
    }

    public void setCadastrada(boolean cadastrada) {
        this.cadastrada = cadastrada;
    }

    public Ideia getIdeia() {
        return ideia;
    }

    public void setIdeia(Ideia ideia) {
        this.ideia = ideia;
    }
}
