package com.anonymous.service;

import com.anonymous.api.input.SignupInput;
import com.anonymous.dto.UserDTO;

public interface IAuthService {
    UserDTO createUserDTO(SignupInput signupInput);
    
}
