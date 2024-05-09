package com.anonymous.dto.request;

import com.anonymous.validator.PasswordConstraint;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthInput {

    @Size(min = 8, message = "USERNAME_INVALID")
    private String username;
    @PasswordConstraint(min = 8, message = "PASSWORD_INVALID")
    private String password;

}
