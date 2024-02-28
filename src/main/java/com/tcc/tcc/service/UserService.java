package com.tcc.tcc.service;

import com.tcc.tcc.dto.UserDto;
import com.tcc.tcc.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void updateUser(UserDto userDto);

    User findByEmail(String email);

    User findById(long id);

    List<UserDto> findAllUsers();

    List<User> findAll();

    List<User> findUserByNameLike(String nome);
}
