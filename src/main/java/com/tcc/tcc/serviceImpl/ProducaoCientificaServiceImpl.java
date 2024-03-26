package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.ProducaoCientifica;
import com.tcc.tcc.repository.ProducaoCientificaRepository;
import com.tcc.tcc.service.ProducaoCientificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducaoCientificaServiceImpl implements ProducaoCientificaService {

    @Autowired
    ProducaoCientificaRepository producaoCientificaRepository;

    @Override
    public List<ProducaoCientifica> findAll() {
        return producaoCientificaRepository.findAll();
    }

    @Override
    public ProducaoCientifica findById(long id) {
        return producaoCientificaRepository.findById(id).get();
    }

    @Override
    public ProducaoCientifica save(ProducaoCientifica producaoCientifica) {
        return producaoCientificaRepository.save(producaoCientifica);
    }
}
