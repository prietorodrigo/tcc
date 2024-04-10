package com.tcc.tcc.repository;

import com.tcc.tcc.model.User;
import com.tcc.tcc.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(Optional<User> user);

    @Modifying
    @Query("DELETE FROM UserRole WHERE id = :id")
    void excluir(Long id);
}
