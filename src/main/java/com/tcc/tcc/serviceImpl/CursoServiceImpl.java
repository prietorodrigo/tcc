package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Curso;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UsuarioCurso;
import com.tcc.tcc.repository.CursoRepository;
import com.tcc.tcc.repository.UserRepository;
import com.tcc.tcc.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso findById(long id) {
        return cursoRepository.findById(id).get();
    }

    @Override
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> findCursosByUserId(Long userId) {
        // Obtener el usuario por su ID
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener la lista de UsuarioCurso asociados al usuario
        List<UsuarioCurso> usuarioCursos = user.getUsuarioCursos();

        // Extraer la lista de cursos de los UsuarioCurso
        List<Curso> cursos = usuarioCursos.stream()
                .map(UsuarioCurso::getCurso)
                .collect(Collectors.toList());

        return cursos;
    }
}
