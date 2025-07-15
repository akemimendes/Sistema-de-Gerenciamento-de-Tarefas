package com.treina.recife.sgp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.treina.recife.sgp.model.Tarefa;

public interface TarefaService{

    Page<Tarefa> getTarefa(Pageable pageable);

    Optional<Tarefa> getTarefaById(long taskId);

    Tarefa createTarefa(Tarefa tarefa);

    Tarefa updateTarefa(Tarefa tarefa);

    void deleteTarefa(long taskId);

    List<Tarefa> findByProjetoProjectId(long projectId);


}
