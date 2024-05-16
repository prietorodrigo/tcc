package com.tcc.tcc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="producaocientifica")
public class ProducaoCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int semestre;

    private int ano;

    @NotBlank
    private String tipo;

    private String coorientador;

    @NotBlank
    @Lob
    private String resumo;

    private String pdf;

    @Lob
    private String apresentacoes;

    @NotBlank
    @Lob
    private String palavrasChaves;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data;

    @Lob
    private String banca;

    private String url;

    @OneToOne
    @JoinColumn(name="proposta_id")
    private Proposta proposta;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCoorientador() {
        return coorientador;
    }

    public void setCoorientador(String coorientador) {
        this.coorientador = coorientador;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
