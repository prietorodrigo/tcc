package com.tcc.tcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name="proposta")
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String tema;

    @NotBlank
    @Lob
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToMany(mappedBy="proposta")
    private List<ProducaoCientifica> producaoCientificas;

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

    public List<ProducaoCientifica> getProducaoCientificas() {
        return producaoCientificas;
    }

    public void setProducaoCientificas(List<ProducaoCientifica> producaoCientificas) {
        this.producaoCientificas = producaoCientificas;
    }
}
