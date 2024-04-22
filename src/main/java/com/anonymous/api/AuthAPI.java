package com.anonymous.api;

import com.anonymous.dto.request.AuthInput;
import com.anonymous.service.IAuthService;
import com.anonymous.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthAPI {

    @Autowired
    private IAuthService authService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthInput authInput) {
        return authService.authentication(authInput.getUsername(), authInput.getPassword());
    }

    @PostMapping("/introspect")
    public ResponseEntity<?> authentication(@RequestParam("token") String token) throws ParseException, JOSEException {
        Boolean isValid = jwtUtil.introspectToken(token);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }

}
