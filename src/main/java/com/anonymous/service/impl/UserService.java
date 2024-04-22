package com.anonymous.service.impl;

import com.anonymous.converter.UserConverter;
import com.anonymous.dto.UserDTO;
import com.anonymous.dto.request.SignupInput;
import com.anonymous.entity.RoleEntity;
import com.anonymous.entity.UserEntity;
import com.anonymous.repository.IRoleRepository;
import com.anonymous.repository.IUserRepository;
import com.anonymous.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    IUserRepository userRepository;
    PasswordEncoder passwordEncoder;
    IRoleRepository roleRepository;
    UserConverter userConverter;

    @Override
    public UserDTO findOneByUserNameAndStatus(String name, Integer status) {
        UserEntity userEntity = userRepository.findOneByUserNameAndStatus(name, 1);
        return userConverter.toDTO(userEntity);
    }

    @Override
    public UserDTO createUser(SignupInput signupInput) {

        UserEntity checkUserNameExist = userRepository.findOneByUserNameAndStatus(signupInput.getUsername(), 1);
        if (checkUserNameExist != null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(signupInput.getUsername());
        userDTO.setPassword(passwordEncoder.encode(signupInput.getPassword()));
        userDTO.setStatus(1);
        UserEntity userEntity = userConverter.toEntity(userDTO);

        RoleEntity roles = roleRepository.findOneByCode(signupInput.getRole());
        userEntity.setRoles(Set.of(roles));

        userEntity = userRepository.save(userEntity);
        return userConverter.toDTO(userEntity);
    }


}
