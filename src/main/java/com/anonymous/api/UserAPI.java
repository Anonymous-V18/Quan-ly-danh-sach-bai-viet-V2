package com.anonymous.api;

import com.anonymous.dto.UserDTO;
import com.anonymous.dto.request.SignupInput;
import com.anonymous.dto.response.ApiResponse;
import com.anonymous.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAPI {

    IUserService userService;

    @PostMapping("users/register")
    public ApiResponse<UserDTO> createAccountUser(@Valid @RequestBody SignupInput signupInput) {
        return this.create(signupInput, "USER");
    }

    @PostMapping("admin/register")
    public ApiResponse<UserDTO> createAccountAdmin(@Valid @RequestBody SignupInput signupInput) {
        return this.create(signupInput, "ADMIN");
    }

    public ApiResponse<UserDTO> create(SignupInput signupInput, String role) {
        signupInput.setRole(role);
        UserDTO userDTO = userService.createUser(signupInput);
        return ApiResponse.<UserDTO>builder().code(10000).result(userDTO).build();
    }

}
