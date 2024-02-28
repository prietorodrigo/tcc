package com.tcc.tcc.service;

import com.tcc.tcc.model.Curso;

import java.util.List;

public interface CursoService {
    List<Curso> findAll();
    Curso findById(long id);
    Curso save(Curso curso);
}
