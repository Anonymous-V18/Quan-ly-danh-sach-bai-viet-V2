package com.anonymous.api;

import com.anonymous.dto.request.AuthInput;
import com.anonymous.dto.response.ApiResponse;
import com.anonymous.dto.response.AuthOutput;
import com.anonymous.dto.response.IntrospectOutput;
import com.anonymous.service.IAuthService;
import com.anonymous.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthAPI {

    IAuthService authService;
    JwtUtil jwtUtil;


    @PostMapping("/login")
    public ApiResponse<AuthOutput> createAuthenticationToken(@RequestBody AuthInput authInput) {
        String accessToken = authService.authentication(authInput.getUsername(), authInput.getPassword());
        AuthOutput response = new AuthOutput(accessToken, accessToken);
        return ApiResponse.<AuthOutput>builder().result(response).build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectOutput> authentication(@RequestParam("token") String token)
            throws ParseException, JOSEException {
        Boolean isValid = jwtUtil.introspectToken(token);
        IntrospectOutput response = new IntrospectOutput(isValid);
        return ApiResponse.<IntrospectOutput>builder().result(response).build();
    }

}
