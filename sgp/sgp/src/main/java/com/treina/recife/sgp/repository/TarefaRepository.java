package com.treina.recife.sgp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Tarefa;


@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    List<Tarefa> findByUsuarioUserId(long userId);

    boolean existsByTitulo(String title);

    boolean existsByUsuarioUserId(long userId);

    List<Tarefa> findByProjetoProjectId(long projectId);

}
