package com.anonymous.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO extends AbstractDTO<UserDTO> {
    private String userName;
    private String password;
    private Integer status;
    private List<RoleDTO> roles = new ArrayList<>();


}