package com.tcc.tcc.repository;


import com.tcc.tcc.model.Proposta;
import com.tcc.tcc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    @Query("SELECT p FROM Proposta p WHERE p.tema LIKE %?1% OR p.descricao LIKE %?1% OR p.area.nome LIKE %?1% OR p.curso.nome LIKE %?1% OR p.estudante.name LIKE %?1% OR p.orientador.name LIKE %?1%")
    List<Proposta> findPropostasLike(String texto);

    List<Proposta> findByOrientador(User orientador);

    List<Proposta> findByEstudante(User estudante);
}
