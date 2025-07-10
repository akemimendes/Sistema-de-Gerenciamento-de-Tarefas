package com.treina.recife.sgp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.treina.recife.sgp.model.Tarefa;
import com.treina.recife.sgp.repository.TarefaRepository;
import com.treina.recife.sgp.service.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService{

    @Autowired
    TarefaRepository tarefaRepository;

    @Override
    public Page<Tarefa> getTarefa(Pageable pageable) {
       return tarefaRepository.findAll(pageable);
    }

    @Override
    public Optional<Tarefa> getTarefaById(long taskId) {
       // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTarefa'");
    }

    @Override
    public Tarefa createTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public Tarefa updateTarefa(Tarefa tarefa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTarefa'");
    }

    @Override
    public void deleteTarefa(long taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTarefa'");
    }

}
