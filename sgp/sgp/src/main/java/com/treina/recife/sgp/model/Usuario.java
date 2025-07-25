package com.treina.recife.sgp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.treina.recife.sgp.constants.StatusUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="Usuarios")
public class Usuario {

    @SuppressWarnings("unused")
    private static final long serialVersionID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private long userId;

    @Column(name="NOME", nullable =false)
    private String nome;

    @Column(name="CPF", nullable =false, unique = true)
    private String cpf;

    @Column(name="email", nullable =false, unique = true)
    private String email;

    @Column(name="SENHA", nullable =false)
    private String senha;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    @Column(name="DATANASCIMENTO", nullable =false)
    private LocalDate dataNascimento;

    @Column(name="STATUS", nullable =false)
    @Enumerated(EnumType.STRING)
    private StatusUsuario status;

    @OneToMany(mappedBy = "responsavel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Projeto> projetos= new ArrayList<Projeto>();

}
