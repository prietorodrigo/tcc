package com.tcc.tcc.repository;

import com.tcc.tcc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    boolean existsByName(String name);
    List<Role> findByIdIn(List<Long> ids);
}
