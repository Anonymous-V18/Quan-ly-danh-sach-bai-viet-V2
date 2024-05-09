package com.anonymous.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO extends AbstractDTO {

    String userName;
    String password;
    Integer status;
    Set<RoleDTO> roles = new HashSet<>();


}