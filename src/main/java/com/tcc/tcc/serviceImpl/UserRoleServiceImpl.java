package com.tcc.tcc.serviceImpl;

import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UserRole;
import com.tcc.tcc.repository.UserRoleRepository;
import com.tcc.tcc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole findById(long id) {
        return userRoleRepository.findById(id).orElse(null);
    }

    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public void delete(UserRole userRole) {
        userRoleRepository.delete(userRole);
    }

    @Override
    public List<UserRole> findByUsuario(User user) {
        return userRoleRepository.findByUser(Optional.ofNullable(user));
    }

    @Override
    public List<Role> findRolesByUsuario(User user) {
        List<UserRole> userRoles = userRoleRepository.findByUser(Optional.ofNullable(user));
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll(List<UserRole> userRoles) {
        userRoleRepository.deleteAll(userRoles);
    }
}
