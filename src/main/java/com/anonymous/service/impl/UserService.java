package com.anonymous.service.impl;

import com.anonymous.converter.UserConverter;
import com.anonymous.dto.UserDTO;
import com.anonymous.entity.UserEntity;
import com.anonymous.repository.UserRepository;
import com.anonymous.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDTO findOneByUserNameAndStatus(String name, Integer status) {
        UserEntity userEntity = userRepository.findOneByUserNameAndStatus(name, 1);
        return userConverter.toDTO(userEntity);
    }
}
