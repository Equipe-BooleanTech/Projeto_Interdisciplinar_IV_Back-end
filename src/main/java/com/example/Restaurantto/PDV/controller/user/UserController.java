package com.example.Restaurantto.PDV.controller.user;

import com.example.Restaurantto.PDV.dto.user.CreateUserDTO;
import com.example.Restaurantto.PDV.dto.auth.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.auth.LoginUserDTO;
import com.example.Restaurantto.PDV.dto.user.UpdatePasswordDTO;
import com.example.Restaurantto.PDV.dto.user.UserDTO;
import com.example.Restaurantto.PDV.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody LoginUserDTO loginUserDTO) {
        JwtTokenDTO token = userService.authenticarUsuario(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Void> salvarUsuario(@RequestBody CreateUserDTO createUserDTO){
        userService.salvarUsuario(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @RequestBody CreateUserDTO createUserDTO){
        userService.atualizarUsuario(id, createUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id){
        userService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update-password")
    public ResponseEntity<Void> mudarSenha(@RequestBody UpdatePasswordDTO updatePasswordDTO){
        userService.mudarSenha(updatePasswordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/get-users")
    public ResponseEntity<List<UserDTO>> listarTodosUsuarios(){
        List<UserDTO> users = userService.listarTodosUsuarios();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
