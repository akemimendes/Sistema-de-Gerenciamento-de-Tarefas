package com.treina.recife.sgp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name="Projeto")
public class Projeto {

    private static final long serialVersionID= 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECTID")
    private long projectId;

    @Column(name = "NOME",nullable = false)
    private String nome;

    @Column(name = "DESCRICAO",nullable = false)
    private String descricao;

    @Column(name = "DATAINICIO",nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dataInicio;

    @Column(name = "DATACONCLUSAO",nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dataConclusao;

    @ManyToOne
    @JoinColumn(name = "USERID",referencedColumnName = "userId", nullable =false)
    private Usuario responsavel;
    
}
