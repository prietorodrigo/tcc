package com.tcc.tcc.service;

import com.tcc.tcc.model.Curso;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UsuarioCurso;

import java.util.List;

public interface UsuarioCursoService {
    List<UsuarioCurso> findAll();
    UsuarioCurso findById(long id);
    UsuarioCurso save(UsuarioCurso usuarioCurso);
    List<Curso> findCursosByUsuario(User user);

}
