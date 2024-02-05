package com.anonymous.service.impl;

import com.anonymous.api.input.SignupInput;
import com.anonymous.converter.UserConverter;
import com.anonymous.dto.UserDTO;
import com.anonymous.entity.RoleEntity;
import com.anonymous.entity.UserEntity;
import com.anonymous.repository.RoleRepository;
import com.anonymous.repository.UserRepository;
import com.anonymous.service.IAuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AuthService implements IAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDTO createUserDTO(SignupInput signupInput) {

        UserEntity users = userRepository.findOneByUserNameAndStatus(signupInput.getUsername(), 1);
        RoleEntity roles = roleRepository.findOneByCode(signupInput.getRole());

        if (users != null) {
            if (users.getRoles().contains(roles)) {
                return null;
            } else {
                users.getRoles().add(roles);
                userRepository.save(users);
                return userConverter.toDTO(users);
            }
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(signupInput.getUsername());
        userDTO.setPassword(passwordEncoder.encode(signupInput.getPassword()));
        userDTO.setStatus(1);

        UserEntity userEntity = userConverter.toEntity(userDTO);
        userEntity.setRoles(List.of(roles));

        userEntity = userRepository.save(userEntity);

        return userConverter.toDTO(userEntity);
    }


}
