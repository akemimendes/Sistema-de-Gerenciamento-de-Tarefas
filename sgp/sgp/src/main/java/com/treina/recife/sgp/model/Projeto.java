package com.treina.recife.sgp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusProjeto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="Projeto")
public class Projeto {

    @SuppressWarnings("unused")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private String dataInicio;

    @Column(name = "DATACONCLUSAO",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private String dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID",referencedColumnName = "userId")
    @JsonBackReference
    private Usuario responsavel;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    
    
}
