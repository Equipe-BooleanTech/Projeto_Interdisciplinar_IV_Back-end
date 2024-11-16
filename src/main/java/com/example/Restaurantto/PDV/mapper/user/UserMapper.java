package com.example.Restaurantto.PDV.mapper.user;

import com.example.Restaurantto.PDV.dto.user.UserDTO;
import com.example.Restaurantto.PDV.model.user.ModelUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "role.name", target = "roles")
    UserDTO ToModelUser(ModelUser user);
    ModelUser ToModelUserDTO(UserDTO userDTO);

}
