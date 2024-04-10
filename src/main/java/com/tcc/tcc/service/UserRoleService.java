package com.tcc.tcc.service;

import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> findAll();
    UserRole findById(long id);
    UserRole save(UserRole userRole);
    void delete(UserRole userRole); // Método para eliminar un UserRole
    List<UserRole> findByUsuario(User user); // Método para encontrar UserRole por usuario
    List<Role> findRolesByUsuario(User user);
    void deleteAll(List<UserRole> userRoles);
}
