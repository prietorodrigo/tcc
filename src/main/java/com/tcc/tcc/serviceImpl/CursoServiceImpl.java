package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Curso;
import com.tcc.tcc.repository.CursoRepository;
import com.tcc.tcc.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    CursoRepository cursoRepository;

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
}
