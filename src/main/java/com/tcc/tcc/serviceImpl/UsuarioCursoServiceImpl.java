package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Curso;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UsuarioCurso;
import com.tcc.tcc.repository.UsuarioCursoRepository;
import com.tcc.tcc.service.UsuarioCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioCursoServiceImpl implements UsuarioCursoService {

    @Autowired
    UsuarioCursoRepository usuarioCursoRepository;

    @Override
    public List<UsuarioCurso> findAll() {
        return usuarioCursoRepository.findAll();
    }

    @Override
    public UsuarioCurso findById(long id) {
        return usuarioCursoRepository.findById(id).orElse(null);
    }

    @Override
    public UsuarioCurso save(UsuarioCurso usuarioCurso) {
        return usuarioCursoRepository.save(usuarioCurso);
    }

    @Override
    public List<Curso> findCursosByUsuario(User user) {
        List<UsuarioCurso> usuarioCursos = usuarioCursoRepository.findByUsuario(user);
        return usuarioCursos.stream()
                .map(UsuarioCurso::getCurso)
                .collect(Collectors.toList());
    }
}
