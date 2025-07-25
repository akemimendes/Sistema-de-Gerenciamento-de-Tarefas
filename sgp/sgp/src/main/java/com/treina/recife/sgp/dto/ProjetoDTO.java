package com.treina.recife.sgp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusProjeto;
import com.treina.recife.sgp.model.Usuario;

public class ProjetoDTO {
    
    
    private String nome;

   
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private String dataInicio;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private String dataConclusao;

    private StatusProjeto status;

    private Usuario responsavel;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    

   
}
