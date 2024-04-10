package com.tcc.tcc.service;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    List<User> findAll();

    List<User> findUserByNameLike(String nome);

    User findByName(String name);
    List<User> findByRole(String roleName);

    User findById(long id);

    User updateUser(User user);
}
