package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Proposta;
import com.tcc.tcc.repository.PropostaRepository;
import com.tcc.tcc.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaServiceImpl implements PropostaService {

    @Autowired
    PropostaRepository propostaRepository;

    @Override
    public List<Proposta> findAll() {
        return propostaRepository.findAll();
    }

    @Override
    public Proposta findById(long id) {
        return propostaRepository.findById(id).get();
    }

    @Override
    public Proposta save(Proposta proposta) {
        return propostaRepository.save(proposta);
    }

    @Override
    public List<Proposta> findPropostasLike(String texto) {
        return propostaRepository.findPropostasLike(texto);
    }
}
