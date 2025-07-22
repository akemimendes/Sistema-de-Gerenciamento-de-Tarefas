package com.treina.recife.sgp.controller;

import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treina.recife.sgp.service.UserService;
import com.treina.recife.sgp.constants.StatusUsuario;
import com.treina.recife.sgp.dto.UsuarioDTO;
import com.treina.recife.sgp.model.Usuario;

@RestController
@RequestMapping("Usuarios")
public class UsuarioController {

    private final UserService usuarioService;

    Logger logger = LogManager.getLogger(UsuarioController.class);

    public UsuarioController(UserService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Usuario> usuarios = usuarioService.getUsuarios(pageable);

        if (usuarios.isEmpty()) {
            logger.info("Ainda não há usuários cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(@PathVariable Long userId) {
        Optional<Usuario> usuarios = usuarioService.getUsuarioById(userId);

        if (usuarios.isEmpty()) {
            logger.info("Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioService.isEmailAlreadyToken(usuarioDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(usuarioDTO.getEmail() + " já está em uso!");
        } else {
            Usuario newUsuario = new Usuario();
            newUsuario.setNome(usuarioDTO.getNome());
            newUsuario.setCpf(usuarioDTO.getCpf());
            newUsuario.setEmail(usuarioDTO.getEmail());
            newUsuario.setSenha(usuarioDTO.getSenha());
            newUsuario.setDataNascimento(usuarioDTO.getDataNascimento());
            newUsuario.setStatus(usuarioDTO.getStatus());

            usuarioService.createUsuario(newUsuario);
            logger.info("Usuário criado com sucesso" + newUsuario.getUserId());

            return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable Long userId, @RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarios = usuarioService.getUsuarioById(userId);
        if (usuarios.isEmpty()) {
            logger.info("Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            Usuario newUsuario = usuarios.get();
            // newUsuario.setUserId(userId);
            newUsuario.setNome(usuarioDTO.getNome());
            newUsuario.setCpf(usuarioDTO.getCpf());
            newUsuario.setEmail(usuarioDTO.getEmail());
            newUsuario.setSenha(usuarioDTO.getSenha());
            newUsuario.setDataNascimento(usuarioDTO.getDataNascimento());
            newUsuario.setStatus(usuarioDTO.getStatus());
            usuarioService.updateUsuario(newUsuario);

            logger.info("Usuário alterado com sucesso" + newUsuario.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(newUsuario);
        }

    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<Object> atualizarStatus(@PathVariable(value = "userId") long userId,
            @RequestBody Map<String, String> body) {
        Optional<Usuario> usuarios = usuarioService.getUsuarioById(userId);

        if (usuarios.isEmpty()) {
            logger.info("Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            String statusBody = body.get("status");
            if (statusBody == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status é obrigatório!");
            } else {
                StatusUsuario novoStatus;
                try{
                    novoStatus= StatusUsuario.valueOf(statusBody.toUpperCase());
                }catch(IllegalArgumentException e){
                    return ResponseEntity.badRequest().body("Status inválido. Valores permitidos: ATIVO, INATIVO");
                }

                Usuario usuarioAtualizado=usuarios.get();
                usuarioAtualizado.setStatus(novoStatus);
                usuarioService.updateUsuario(usuarioAtualizado);
                 logger.info("Status do usuário atualizado com sucesso.");

                 return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
            }
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable Long userId) {
        Optional<Usuario> usuarios = usuarioService.getUsuarioById(userId);

        if (usuarios.isEmpty()) {
            logger.info("Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            usuarioService.deleteUsuario(userId);
            logger.info("Usuário deletado com sucesso!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso!");
        }
    }

}
