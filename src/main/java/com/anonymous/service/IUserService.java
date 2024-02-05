package com.anonymous.service;

import com.anonymous.dto.UserDTO;

public interface IUserService {
    UserDTO findOneByUserNameAndStatus(String name, Integer status);
}
