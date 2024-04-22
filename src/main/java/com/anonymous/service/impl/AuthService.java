package com.anonymous.service.impl;

import com.anonymous.converter.UserConverter;
import com.anonymous.entity.UserEntity;
import com.anonymous.repository.IUserRepository;
import com.anonymous.service.IAuthService;
import com.anonymous.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {


    IUserRepository IUserRepository;
    PasswordEncoder passwordEncoder;
    UserConverter userConverter;
    JwtUtil jwtUtil;


    @Override
    public String authentication(String username, String password) {
        UserEntity user = IUserRepository.findOneByUserNameAndStatus(username, 1);
        if (user == null) {
            throw new RuntimeException("Could not find user !");
        }
        return passwordEncoder.matches(password, user.getPassword()) ? jwtUtil.generateToken(user) : null;
    }


}
