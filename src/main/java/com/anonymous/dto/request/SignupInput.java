package com.anonymous.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupInput {
    private String username;
    private String password;
    @Setter
    private String role;

}
