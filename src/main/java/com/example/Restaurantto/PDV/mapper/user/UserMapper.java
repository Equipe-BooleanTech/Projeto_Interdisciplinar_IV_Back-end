package com.example.Restaurantto.PDV.mapper.user;

import com.example.Restaurantto.PDV.dto.user.UserDTO;
import com.example.Restaurantto.PDV.model.user.ModelUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO ToModelUser(ModelUser user);
    ModelUser ToModelUserDTO(UserDTO userDTO);

}
