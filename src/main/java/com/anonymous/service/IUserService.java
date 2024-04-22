package com.anonymous.service;

import com.anonymous.dto.UserDTO;
import com.anonymous.dto.request.SignupInput;

public interface IUserService {

    UserDTO findOneByUserNameAndStatus(String name, Integer status);

    UserDTO createUser(SignupInput signupInput);

}
