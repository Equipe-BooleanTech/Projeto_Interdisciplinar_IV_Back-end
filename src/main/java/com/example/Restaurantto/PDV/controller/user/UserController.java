package com.example.Restaurantto.PDV.controller.user;

import com.example.Restaurantto.PDV.dto.user.*;
import com.example.Restaurantto.PDV.dto.auth.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.auth.LoginUserDTO;
import com.example.Restaurantto.PDV.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        JwtTokenDTO token = userService.authenticarUsuario(loginUserDTO);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/create-complete")
    public ResponseEntity<UUID> salvarUsuario(@RequestBody @Valid CreateUserDTO createUserDTO) {
        UUID id = userService.salvarUsuario(createUserDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @PostMapping("/prospects")
    public ResponseEntity<UUID> salvarUsuarioProspeccao(@RequestBody @Valid ProspectingUserDTO prospectingUserDTO) {
        UUID id = userService.salvarUsuarioProspeccao(prospectingUserDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @PutMapping("/activate/{id}")
    public ResponseEntity<Void> ativarUsuario(@PathVariable UUID id, @RequestBody @Valid CreateUserDTO createUserDTO) {
        userService.ativarUsuario(id, createUserDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid CreateUserDTO createUserDTO) {
        userService.atualizarUsuario(id, createUserDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable UUID id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/update-password")
    public ResponseEntity<String> mudarSenha(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        userService.mudarSenha(updatePasswordDTO);
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }


    @GetMapping("/get-users")
    public ResponseEntity<Page<UserDTO>> listarTodosUsuarios(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> users = (Page<UserDTO>) userService.listarTodosUsuarios(PageRequest.of(page, size));
        return ResponseEntity.ok(users);
    }


    @PutMapping("/roles/{id}")
    public ResponseEntity<Void> atualizarRoles(@PathVariable UUID id, @RequestBody @Valid UpdateRoleDTO updateRoleDTO) {
        userService.atualizaRole(id, updateRoleDTO);
        return ResponseEntity.ok().build();
    }
}
