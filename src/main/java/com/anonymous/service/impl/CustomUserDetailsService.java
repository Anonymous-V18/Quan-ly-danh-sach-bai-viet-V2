package com.anonymous.service.impl;

import com.anonymous.dto.MyUserDetail;
import com.anonymous.dto.RoleDTO;
import com.anonymous.dto.UserDTO;
import com.anonymous.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findOneByUserNameAndStatus(username, 1);
        if (userDTO == null) {
            throw new UsernameNotFoundException("User not found " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
        }

        return new MyUserDetail(
                username, userDTO.getPassword(),
                true, true,
                true, true,
                authorities
        );
    }
}
