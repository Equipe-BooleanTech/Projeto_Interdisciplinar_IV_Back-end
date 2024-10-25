package com.example.Restaurantto.PDV.controller.user;

import com.example.Restaurantto.PDV.dto.user.*;
import com.example.Restaurantto.PDV.dto.auth.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.auth.LoginUserDTO;
import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.model.user.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<Void> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        userService.atualizarUsuario(id, updateUserDTO);
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
    public ResponseEntity<?> atualizarRole(@PathVariable UUID id, @Valid @RequestBody UpdateRoleDTO updateRoleDTO, Authentication authentication) {
        try {
            // Verifica se o usuário autenticado é um administrador
            ModelUserDetailsImpl userDetails = (ModelUserDetailsImpl) authentication.getPrincipal();

            // Cheque se o usuário autenticado tem permissão de ADMIN
            if (userDetails.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado. Somente administradores podem alterar roles.");
            }



            // Atualiza o role do usuário identificado pelo ID
            userService.atualizaRole(id, updateRoleDTO);

            return ResponseEntity.ok("Role do usuário atualizada com sucesso!");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Role inválida: " + updateRoleDTO.roles());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar role");
        }
    }


}

