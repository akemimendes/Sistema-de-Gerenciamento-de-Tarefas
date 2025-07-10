package com.treina.recife.sgp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    Optional<Projeto> findByResponsavelUserId(long userId);

    Optional<Projeto> findByProjectId(long projectId);

}
