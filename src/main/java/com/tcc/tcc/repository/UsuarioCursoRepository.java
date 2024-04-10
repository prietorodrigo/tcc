package com.tcc.tcc.repository;

import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UsuarioCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioCursoRepository extends JpaRepository<UsuarioCurso, Long> {
    List<UsuarioCurso> findByUsuario(User usuario);
}
