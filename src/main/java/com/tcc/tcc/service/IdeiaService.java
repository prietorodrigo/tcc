package com.tcc.tcc.service;

import com.tcc.tcc.model.Ideia;

import java.util.List;

public interface IdeiaService {
    List<Ideia> findAll();
    Ideia findById(long id);
    Ideia save(Ideia ideia);
    List<Ideia> findIdeiasByReservada(boolean reservada);
}
