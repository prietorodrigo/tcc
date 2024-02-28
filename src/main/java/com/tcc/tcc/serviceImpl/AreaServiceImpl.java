package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Area;
import com.tcc.tcc.repository.AreaRepository;
import com.tcc.tcc.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaRepository areaRepository;

    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    @Override
    public Area findById(long id) {
        return areaRepository.findById(id).get();
    }

    @Override
    public Area save(Area area) {
        return areaRepository.save(area);
    }
}
