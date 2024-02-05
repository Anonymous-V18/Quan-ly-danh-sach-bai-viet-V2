package com.anonymous.api;

import com.anonymous.api.input.AuthInput;
import com.anonymous.api.input.SignupInput;
import com.anonymous.api.output.AuthOutput;
import com.anonymous.dto.UserDTO;
import com.anonymous.service.IAuthService;
import com.anonymous.service.impl.CustomUserDetailsService;
import com.anonymous.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthAPI {
    @Autowired
    private IAuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("users/register")
    public ResponseEntity<?> createUser(@RequestBody SignupInput signupInput) {
        signupInput.setRole("USER");
        UserDTO userDTO = authService.createUserDTO(signupInput);
        if (userDTO == null) {
            return new ResponseEntity<>("User isn't created because USERNAME with YOUR ROLE already exists, try again later !!!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("admin/register")
    public ResponseEntity<?> createAdmin(@RequestBody SignupInput signupInput) {
        signupInput.setRole("ADMIN");
        UserDTO userDTO = authService.createUserDTO(signupInput);
        if (userDTO == null) {
            return new ResponseEntity<>("User isn't created because USERNAME with YOUR ROLE already exists, try again later !!!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthOutput createAuthenticationToken(@RequestBody AuthInput authInput, HttpServletResponse response)
            throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authInput.getUsername(), authInput.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authInput.getUsername());

        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthOutput(token);
    }

}
