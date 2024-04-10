package com.tcc.tcc.repository;


import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.Role;
import com.tcc.tcc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findUserByNameLike(String nome);
    User findByName(String name);
    List<User> findByUserRoles_Role_Name(String roleName);
}
