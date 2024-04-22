package com.anonymous.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO extends AbstractDTO<UserDTO> {

    String userName;
    String password;
    Integer status;
    Set<RoleDTO> roles = new HashSet<>();


}