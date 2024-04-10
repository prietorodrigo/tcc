package com.tcc.tcc.service;

import com.tcc.tcc.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(long id);
    Role save(Role role);
    Role findByName(String name); // Método para encontrar un Role por nombre
    List<Role> findByIdIn(List<Long> ids); // Nuevo método
}
