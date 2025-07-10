package com.treina.recife.sgp.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpfAndEmail(String cpf, String email);

    boolean existsByEmail(String email);

}
