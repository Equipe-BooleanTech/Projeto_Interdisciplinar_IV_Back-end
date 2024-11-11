package com.example.Restaurantto.PDV.controller.user;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.product.TimeSupplierSummaryDTO;
import com.example.Restaurantto.PDV.dto.user.*;
import com.example.Restaurantto.PDV.dto.auth.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.auth.LoginUserDTO;
import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.exception.user.UserCreationFailedException;
import com.example.Restaurantto.PDV.exception.user.UserDeletionFailedException;
import com.example.Restaurantto.PDV.exception.user.UserNotFoundException;
import com.example.Restaurantto.PDV.exception.user.UserUpdateFailedException;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.model.user.ModelUser;
import com.example.Restaurantto.PDV.model.user.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.response.SuccessResponse;
import com.example.Restaurantto.PDV.response.UpdateResponse;
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
import java.util.Map;
import java.util.Optional;
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
    public ResponseEntity<SuccessResponse> salvarUsuario(@RequestBody @Valid CreateUserDTO createUserDTO) {
        try {
            UUID id = userService.salvarUsuario(createUserDTO);
            SuccessResponse response = new SuccessResponse("USUÁRIO CRIADO COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserCreationFailedException e) {
            throw e;
        }

    }


    @PostMapping("/prospects")
    public ResponseEntity<SuccessResponse> salvarUsuarioProspeccao(@RequestBody @Valid ProspectingUserDTO prospectingUserDTO) {
        try {
            UUID id = userService.salvarUsuarioProspeccao(prospectingUserDTO);
            SuccessResponse response = new SuccessResponse("POSPECÇÃO CRIADA COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (UserCreationFailedException e){
            throw e;
        }
    }


    @PutMapping("/activate/{id}")
    public ResponseEntity<SuccessResponse> ativarUsuario(@PathVariable UUID id, @RequestBody @Valid CreateUserDTO createUserDTO) {
        try {
            userService.ativarUsuario(id, createUserDTO);
            SuccessResponse response = new SuccessResponse("POSPECÇÃO ATIVADA COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (UserCreationFailedException e){
            throw e;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateResponse> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        try {
            userService.atualizarUsuario(id, updateUserDTO);
            UpdateResponse response = new UpdateResponse("USUÁRIO ATUALIZADO COM SUCESSO!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UserUpdateFailedException e){
            throw  e;
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessResponse> removerUsuario(@PathVariable UUID id) {
        try {
            userService.deletarUsuario(id);
            SuccessResponse response = new SuccessResponse("USUÁRIO DELETADO COM SUCESSO!", id);
            return ResponseEntity.noContent().build();
        }catch (UserDeletionFailedException e){
            throw e;
        }
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
    @GetMapping("/get-users-by-id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
        try{
        Optional<ModelUser> user = userService.listarPeloId(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("USUÁRIO NÃO ENCONTRADO");
        }}catch (UserNotFoundException e){
            throw e;
        }
    }


    @PutMapping("/roles/{id}")
    public ResponseEntity<?> atualizarRole(@PathVariable UUID id, @Valid @RequestBody UpdateRoleDTO updateRoleDTO, Authentication authentication) {
        try {
            ModelUserDetailsImpl userDetails = (ModelUserDetailsImpl) authentication.getPrincipal();

            if (userDetails.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado. Somente administradores podem alterar roles.");
            }

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


    @PostMapping("/list-users-by-period")
    public ResponseEntity<?> listarUsuariosPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        // ATENÇÃO SE PASSAR A URL NORMAL ELE VAI LISTAR POR MÊS
        // PASSANDO A URL ASSIM list-users-by-period?groupingType=weekly ELE LISTA POR SEMANA
        // PASSANDO A URL ASSIM list-users-by-period?groupingType=yearly ELE LISTA POR ANO
        Map<String, TimeUsersSummaryDTO> usuariosPorPeriodo = userService.listarUsuariosPorPeriodo(dateRangeDTO, groupingType);

        if (usuariosPorPeriodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUM USUARIO ENCONTRADO NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(usuariosPorPeriodo);
        }
    }
}

