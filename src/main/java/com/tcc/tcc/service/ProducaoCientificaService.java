package com.tcc.tcc.service;

import com.tcc.tcc.model.ProducaoCientifica;
import java.util.List;

public interface ProducaoCientificaService {
    List<ProducaoCientifica> findAll();
    ProducaoCientifica findById(long id);
    ProducaoCientifica save(ProducaoCientifica producaoCientifica);
}
