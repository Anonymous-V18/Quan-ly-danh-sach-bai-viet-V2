package com.anonymous.api;

import com.anonymous.dto.UserDTO;
import com.anonymous.dto.request.SignupInput;
import com.anonymous.dto.response.ApiResponse;
import com.anonymous.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserAPI {

    @Autowired
    private IUserService userService;

    @PostMapping("users/register")
    public ResponseEntity<?> createAccountUser(@RequestBody SignupInput signupInput) {
        return this.create(signupInput, "USER");
    }

    @PostMapping("admin/register")
    public ResponseEntity<?> createAccountAdmin(@RequestBody SignupInput signupInput) {
        return this.create(signupInput, "ADMIN");
    }

    public ResponseEntity<?> create(SignupInput signupInput, String role) {
        signupInput.setRole(role);
        UserDTO userDTO = userService.createUser(signupInput);
        if (userDTO == null) {

            return new ResponseEntity<>(ApiResponse.<SignupInput>builder()
                    .statusCode(400)
                    .message("User isn't created because USERNAME already exists, try again later !!!!")
                    .build(),
                    HttpStatus.CREATED);

        }
        return new ResponseEntity<>(ApiResponse.builder().statusCode(10000).result(userDTO).build(), HttpStatus.CREATED);
    }

}
