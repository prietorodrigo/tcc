package com.tcc.tcc.service;

import com.tcc.tcc.model.Proposta;

import java.util.List;

public interface PropostaService {
    List<Proposta> findAll();
    Proposta findById(long id);
    Proposta save(Proposta proposta);
    List<Proposta> findPropostasLike(String texto);
}
