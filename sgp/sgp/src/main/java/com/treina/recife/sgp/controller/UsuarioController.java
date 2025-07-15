package com.treina.recife.sgp.controller;


import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treina.recife.sgp.service.UserService;
import com.treina.recife.sgp.model.Usuario;



@RestController
@RequestMapping("Usuarios")
public class UsuarioController {

    private final UserService usuarioService;

    Logger logger= LogManager.getLogger(UsuarioController.class);

    public UsuarioController(UserService usuarioService){
        this.usuarioService=usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(@PageableDefault(sort = "userId", direction =  Sort.Direction.ASC)Pageable pageable){

        Page<Usuario> usuarios= usuarioService.getUsuarios(pageable);

        if (usuarios.isEmpty()){
            logger.info("Ainda não há usuários cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }

        
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(@PathVariable Long userId){
        Optional<Usuario> usuarios= usuarioService.getUsuarioById(userId);

        if (usuarios.isEmpty()){
            logger.info("Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }
} 


}
