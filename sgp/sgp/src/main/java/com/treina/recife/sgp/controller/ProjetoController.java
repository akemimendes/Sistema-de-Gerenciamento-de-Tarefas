package com.treina.recife.sgp.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina.recife.sgp.constants.StatusProjeto;
import com.treina.recife.sgp.dto.ProjetoDTO;
import com.treina.recife.sgp.dto.UsuarioDTO;
import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.ProjetoService;
import com.treina.recife.sgp.service.UserService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UserService usuarioService;

    Logger logger = LogManager.getLogger(ProjetoController.class);

    public ProjetoController(ProjetoService projetoService, UserService usuarioService) {
        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Object> getProjetos(
            @PageableDefault(sort = "projectId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Projeto> projetos = projetoService.getProjeto(pageable);

        if (projetos.isEmpty()) {
            logger.info("Ainda não há projeto cadastrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ainda não há projeto cadastrado!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(projetos);
        }

    }

    @GetMapping("/{projetoId}")
    public ResponseEntity<Object> getProjetoById(@PathVariable(value = "projetoId") long projectId) {
        Optional<Projeto> projetos = projetoService.getProjetoById(projectId);

        if (projetos.isEmpty()) {
            logger.info("Ainda não há projeto cadastrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ainda não há projeto cadastrado!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(projetos);
        }

    }

    @PostMapping
    public ResponseEntity<Object> createProjeto(@RequestBody ProjetoDTO projetoDTO) {

        Projeto novoProjeto = new Projeto();

        novoProjeto.setNome(projetoDTO.getNome());
        novoProjeto.setDescricao(projetoDTO.getDescricao());
        novoProjeto.setDataConclusao(projetoDTO.getDataConclusao());
        novoProjeto.setDataInicio(projetoDTO.getDataInicio());
        novoProjeto.setStatus(StatusProjeto.ATIVO);
        projetoService.createProjeto(novoProjeto);

        logger.info("Projeto criado com sucesso" + novoProjeto.getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);

    }

    @PutMapping("/{projetoId}")
    public ResponseEntity<Object> updateProjeto(@PathVariable(value = "projetoId") long projectId,
            @RequestBody ProjetoDTO projetoDTO) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isPresent()) {
            Projeto novoProjeto = projeto.get();
            novoProjeto.setNome(projetoDTO.getNome());
            novoProjeto.setDescricao(projetoDTO.getDescricao());
            novoProjeto.setDataConclusao(projetoDTO.getDataConclusao());
            novoProjeto.setDataInicio(projetoDTO.getDataInicio());
            novoProjeto.setStatus(StatusProjeto.ATIVO);
            projetoService.updateProjeto(novoProjeto);

            logger.info("Projeto alterado com sucesso" + novoProjeto.getNome());

            return ResponseEntity.status(HttpStatus.OK).body(novoProjeto);

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Projeto não encontrado!");
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteProjeto(@PathVariable(value = "projectId") long projectId) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        } else {
            projetoService.deleteProjeto(projectId);
            logger.info("Projeto {} deletado com sucesso", projectId);
            return ResponseEntity.status(HttpStatus.OK).body("Projeto deletado com sucesso");
        }
    }

    @PatchMapping("/{projectId}/responsavel")
    public ResponseEntity<Object> atualizarResponsavel(@PathVariable(value = "projectId") long projetoID,
            @RequestBody UsuarioDTO usuarioDTO) {
        Optional<Projeto> projetos = projetoService.getProjetoById(projetoID);

        if (projetos.isEmpty()) {
            logger.info("Projeto não encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado!");
        } else {
            Optional<Usuario> usuarios = usuarioService.getUsuarioById(usuarioDTO.getUserId());
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário não encontrado!");
            } else {
                Projeto projeto = projetos.get();
                Usuario responsavel = usuarios.get();

                projeto.setResponsavel(responsavel);

                Projeto projetoAtualizado = projetoService.createProjeto(projeto);
                logger.info("Responsável {} designado ao projeto {} com sucesso", responsavel, projeto.getNome());

                return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
            }
        }
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<Object> atualizarStatus(@PathVariable(value = "projectId") long projetoID,
             @RequestBody Map<String, String> body)  {
        Optional<Projeto> projetos = projetoService.getProjetoById(projetoID);

        if (projetos.isEmpty()) {
            logger.info("Projeto não encontrado!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Projeto não encontrado!");
        } else {
            String status = body.get("status");
            if (status.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Obrigatório informar o status!");
            } else {
                Projeto projeto = projetos.get();
                projeto.setStatus(StatusProjeto.valueOf(status));
                Projeto projetoAtualizado = projetoService.updateProjeto(projeto);
                logger.info("Status {} designado ao projeto {} com sucesso", status, projeto.getNome());

                return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
            }
        }
    }

}
