package com.tcc.tcc.service;

import com.tcc.tcc.model.Area;

import java.util.List;

public interface AreaService {
    List<Area> findAll();
    Area findById(long id);
    Area save(Area area);
}
