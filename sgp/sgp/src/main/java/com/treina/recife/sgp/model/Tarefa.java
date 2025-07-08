package com.treina.recife.sgp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.Prioridade;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="Tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASKID")
    private long taskId;

    @Column(name="TITULO", nullable =false)
    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name="DATACRIACAO", nullable =false)
    private LocalDate dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name="DATACONCLUSAO", nullable =false)
    private LocalDate dataConclusao;

    @Column(name="PRIORIDADE", nullable =false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "PROJECTID",referencedColumnName = "PROJECTID", nullable =false)
    private Projeto projectId;

    @ManyToOne
    @JoinColumn(name = "USERID",referencedColumnName = "USERID", nullable =false)
    private Usuario userID;
    
}
