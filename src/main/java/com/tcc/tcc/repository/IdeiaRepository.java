package com.tcc.tcc.repository;

import com.tcc.tcc.model.Ideia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeiaRepository extends JpaRepository<Ideia, Long> {
    List<Ideia> findIdeiasByReservada(boolean reservada);
}
