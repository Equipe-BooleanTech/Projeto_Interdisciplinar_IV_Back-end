package com.example.Restaurantto.PDV.service.user;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.product.TimeSupplierSummaryDTO;
import com.example.Restaurantto.PDV.dto.user.*;
import com.example.Restaurantto.PDV.dto.auth.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.auth.LoginUserDTO;
import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.exception.user.EmailAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.user.InvalidCredentialsException;
import com.example.Restaurantto.PDV.model.product.Supplier;
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
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UUID salvarUsuarioProspeccao(ProspectingUserDTO prospectingUserDTO) {
        if (userRepository.findByEmail(prospectingUserDTO.email()).isPresent()) {
            throw new EmailAlreadyRegisteredException("E-MAIL JÁ CADASTRADO");
        }


        ModelUser newUser = ModelUser.builder()
                .email(prospectingUserDTO.email())
                .role(ModelRole.builder().name(Role.ROLE_INACTIVE).build())
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
        user.setRole(ModelRole.builder().name(Role.ROLE_USER).build());


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

        ModelUser newUser = ModelUser.builder()
                .email(createUserDTO.email())
                .password(securityConfig.passwordEncoder().encode(createUserDTO.password()))
                .role(ModelRole.builder().name(Role.valueOf(createUserDTO.role())).build())
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

    private UserDTO mapToUserDTO(ModelUser user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getRole().getName(),
                user.getFullName(),
                user.getPhone(),
                user.getCpf(),
                user.getCep(),
                user.getAddress(),
                user.getAddressNumber(),
                user.getCity(),
                user.getState(),
                user.getNeighborhood(),
                user.getCnpj(),
                user.getMessage(),
                user.getEnterprise(),
                user.isProspecting(),
                user.isEmployee(),
                user.getFunction(),
                user.getPassword(),
                user.getCreatedAt()
        );
    }

    public Page<UserDTO> listarTodosUsuarios(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest)
                .map(this::mapToUserDTO);
    }

    public Optional<ModelUser> listarPeloId(UUID id) {
        return userRepository.findById(id);
    }

    public Map<String, TimeUsersSummaryDTO> listarUsuariosPorPeriodo(DateRangeDTO dateRangeDTO, String groupingType) {
        List<ModelUser> users = userRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        return users.stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.groupingBy(
                        user -> {
                            switch (groupingType.toLowerCase()) {
                                case "weekly":
                                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                                    int weekNumber = user.createdAt().get(weekFields.weekOfWeekBasedYear());
                                    int weekYear = user.createdAt().getYear();
                                    return STR."Week \{weekNumber}, \{weekYear}";
                                case "yearly":
                                    int year = user.createdAt().getYear();
                                    return STR."Year \{year}";
                                default:
                                    return STR."\{user.createdAt()
                                            .getMonth().
                                            getDisplayName(TextStyle.FULL, Locale.getDefault())
                                            }\{user.createdAt().getYear()}";
                            }
                        },
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                userList -> new TimeUsersSummaryDTO(userList, userList.size())
                        )
                ));
    }

}
