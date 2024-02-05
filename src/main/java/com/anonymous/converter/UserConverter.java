package com.anonymous.converter;

import com.anonymous.dto.UserDTO;
import com.anonymous.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper mapper;

    public UserEntity toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, UserEntity.class);
    }

    public UserDTO toDTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }

}
