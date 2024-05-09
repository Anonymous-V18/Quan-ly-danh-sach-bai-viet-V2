package com.anonymous.service.impl;

import com.anonymous.entity.UserEntity;
import com.anonymous.exception.AppException;
import com.anonymous.exception.ErrorCode;
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


    IUserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;


    @Override
    public String authentication(String username, String password) {
        UserEntity user = userRepository.findOneByUserNameAndStatus(username, 1);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return passwordEncoder.matches(password, user.getPassword()) ? jwtUtil.generateToken(user) : null;
    }


}
