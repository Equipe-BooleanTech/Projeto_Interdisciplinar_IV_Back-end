package com.example.Restaurantto.PDV.service.user;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.user.*;
import com.example.Restaurantto.PDV.dto.auth.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.auth.LoginUserDTO;
import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.exception.user.EmailAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.user.InvalidCredentialsException;
import com.example.Restaurantto.PDV.exception.user.UserNotFoundException;
import com.example.Restaurantto.PDV.mapper.user.UserMapper;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.model.user.ModelUser;
import com.example.Restaurantto.PDV.model.user.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.repository.user.RoleRepository;
import com.example.Restaurantto.PDV.repository.user.UserRepository;
import com.example.Restaurantto.PDV.config.security.SecurityConfig;

import com.example.Restaurantto.PDV.service.auth.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final  RoleRepository roleRepository;
    private final SecurityConfig securityConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, SecurityConfig securityConfig, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityConfig = securityConfig;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }


    public UUID buscarIdPorEmail(String email) {
        ModelUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(STR."Usuário não encontrado com o email: \{email}"));
        return user.getId();
    }

    public String buscarNomePorEmail(String email) {
        ModelUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(STR."Usuário não encontrado com o email: \{email}"));
        return user.getFullName();
    }

    public UUID salvarUsuarioProspeccao(ProspectingUserDTO prospectingUserDTO) {
        if (userRepository.findByEmail(prospectingUserDTO.email()).isPresent()) {
            throw new EmailAlreadyRegisteredException("E-MAIL JÁ CADASTRADO");
        }
        ModelRole role = roleRepository.findByName(Role.valueOf(Role.ROLE_INACTIVE.name()))
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        ModelUser newUser = ModelUser.builder()
                .email(prospectingUserDTO.email())
                .role(role)
                .fullName(prospectingUserDTO.fullName())
                .phone(prospectingUserDTO.phone())
                .enterprise(prospectingUserDTO.enterprise())
                .message(prospectingUserDTO.message())
                .isProspecting(true)
                .isEmployee(false)
                .cpf(null)
                .cep(null)
                .address(null)
                .addressNumber(0)
                .city(null)
                .state(null)
                .neighborhood(null)
                .cnpj(null)
                .createdAt(LocalDate.now())
                .build();

        userRepository.save(newUser);
        return newUser.getId();
    }


    private boolean isInformacoesCompletas(ModelUser user) {
        return user.getCpf() != null && !user.getCpf().isEmpty()
                && user.getCep() != null && !user.getCep().isEmpty()
                && user.getAddress() != null && !user.getAddress().isEmpty()
                && user.getAddressNumber() > 0
                && user.getCity() != null && !user.getCity().isEmpty()
                && user.getState() != null && !user.getState().isEmpty()
                && user.getNeighborhood() != null && !user.getNeighborhood().isEmpty()
                && user.getCnpj() != null && !user.getCnpj().isEmpty()
                && user.getPassword() != null && !user.getPassword().isEmpty();
    }

    public void ativarUsuario(UUID id, CreateUserDTO createUserDTO) {
        ModelUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO"));

        if (!user.isProspecting()) {
            throw new IllegalStateException("O USUÁRIO JÁ ESTÁ ATIVADO");
        }
        ModelRole role = roleRepository.findByName(Role.valueOf(Role.ROLE_USER.name()))
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        user.setCpf(createUserDTO.cpf());
        user.setCep(createUserDTO.cep());
        user.setAddress(createUserDTO.address());
        user.setAddressNumber(createUserDTO.addressNumber());
        user.setCity(createUserDTO.city());
        user.setState(createUserDTO.state());
        user.setNeighborhood(createUserDTO.neighborhood());
        user.setCnpj(createUserDTO.cnpj());
        user.setPhone(createUserDTO.phone());
        user.setFullName(createUserDTO.fullName());
        user.setEnterprise(createUserDTO.enterprise());
        user.setEmployee(createUserDTO.isEmployee());
        user.setRole(role);


        user.setProspecting(false);

        if (createUserDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        } else {
            throw new IllegalArgumentException("SENHA OBRIGATÓRIA NA ATIVAÇÃO");
        }

        if (!isInformacoesCompletas(user)) {
            throw new IllegalArgumentException("INFORMAÇÕES INCOMPLETAS PARA ATIVAÇÃO");
        }

        userRepository.save(user);
    }



    public UUID salvarUsuario(CreateUserDTO createUserDTO){
        if(userRepository.findByEmail(createUserDTO.email()).isPresent()){
            throw new EmailAlreadyRegisteredException("E-MAIL JÁ CADASTRADO");
        }

        ModelRole role = roleRepository.findByName(Role.valueOf(createUserDTO.role()))
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        ModelUser newUser = ModelUser.builder()
                .email(createUserDTO.email())
                .password(securityConfig.passwordEncoder().encode(createUserDTO.password()))
                .role(role)
                .fullName(createUserDTO.fullName())
                .phone(createUserDTO.phone())
                .cpf(createUserDTO.cpf())
                .cep(createUserDTO.cep())
                .address(createUserDTO.address())
                .addressNumber(createUserDTO.addressNumber())
                .city(createUserDTO.city())
                .state(createUserDTO.state())
                .neighborhood(createUserDTO.neighborhood())
                .cnpj(createUserDTO.cnpj())
                .message(createUserDTO.message())
                .enterprise(createUserDTO.enterprise())
                .isProspecting(createUserDTO.isProspecting())
                .isEmployee(createUserDTO.isEmployee())
                .function(createUserDTO.function())
                .createdAt(LocalDate.now())
                .build();
        userRepository.save(newUser);

        return newUser.getId();
    }

    public void atualizarUsuario(UUID id, UpdateUserDTO updateUserDTO){
        ModelUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO"));

        user.setEmail(updateUserDTO.email());
        user.setFullName(updateUserDTO.fullName());
        user.setPhone(updateUserDTO.phone());
        user.setCpf(updateUserDTO.cpf());
        user.setCep(updateUserDTO.cep());
        user.setAddress(updateUserDTO.address());
        user.setAddressNumber(updateUserDTO.addressNumber());
        user.setCity(updateUserDTO.city());
        user.setState(updateUserDTO.state());
        user.setNeighborhood(updateUserDTO.neighborhood());
        user.setCnpj(updateUserDTO.cnpj());
        user.setMessage(updateUserDTO.message());
        user.setEnterprise(updateUserDTO.enterprise());
        user.setProspecting(updateUserDTO.isProspecting());
        user.setEmployee(updateUserDTO.isEmployee());
        user.setFunction(updateUserDTO.function());

        userRepository.save(user);
    }

    public void atualizaRole(UUID id, UpdateRoleDTO updateRoleDTO) {
        ModelUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO"));

        ModelRole role = roleRepository.findByName(Role.valueOf(updateRoleDTO.roles()))
                .orElseGet(() -> ModelRole.builder().name(Role.valueOf(updateRoleDTO.roles())).build());

        user.setRole(role);

        userRepository.save(user);
    }



    public void deletarUsuario(UUID id){
        if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO");
        }
        userRepository.deleteById(id);
    }
    public void mudarSenha(UpdatePasswordDTO updatePasswordDTO) {
        ModelUser user = userRepository.findByEmail(updatePasswordDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO"));

        if (!passwordEncoder.matches(updatePasswordDTO.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("SENHA ATUAL INCORRETA");
        }

        if (!isPasswordStrong(updatePasswordDTO.newPassword())) {
            throw new IllegalArgumentException("A NOVA SENHA NÃO ATENDE AOS CRITÉRIOS DE SEGURANÇA");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && password.matches(".*[!@#$%^&*()].*"); //Aqui a senha tem que ter ate 8 letras ou numeros e tem que ter um especial
    }

    public JwtTokenDTO authenticarUsuario(LoginUserDTO loginUserDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.email(),
                loginUserDTO.password());

        try {
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
            String token = jwtTokenService.generateToken(modelUserDetails);
            return new JwtTokenDTO(token);

        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("FALHA NA AUTENTICAÇÃO: CREDENCIAIS INVÁLIDAS");
        } catch (Exception e) {
            throw new RuntimeException("ERRO INESPERADO NA AUTENTICAÇÃO: " + e.getMessage());
        }
    }


    public Page<UserDTO> listarTodosUsuarios(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest)
                .map(UserMapper.INSTANCE::ToModelUser);
    }

    public Optional<ModelUser> listarPeloId(UUID id) {
        return userRepository.findById(id);
    }

    public TimeSummaryDTO listarUsuariosPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<ModelUser> users = userRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        List<UserDTO> userList = users.stream()
                .map(UserMapper.INSTANCE::ToModelUser)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(userList), userList.size());
    }

}
