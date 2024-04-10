package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Ideia;
import com.tcc.tcc.repository.IdeiaRepository;
import com.tcc.tcc.service.IdeiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IdeiaServiceImpl implements IdeiaService {
    @Autowired
    IdeiaRepository ideiaRepository;

    @Override
    public List<Ideia> findAll() {
        return ideiaRepository.findAll();
    }

    @Override
    public Ideia findById(long id) {
        return ideiaRepository.findById(id).get();
    }

    @Override
    public Ideia save(Ideia ideia) {
        return ideiaRepository.save(ideia);
    }

    @Override
    public List<Ideia> findIdeiasByReservada(boolean reservada) {
        return ideiaRepository.findIdeiasByReservada(reservada);
    }
}
