package com.anonymous.service.impl;

import com.anonymous.dto.UserDTO;
import com.anonymous.dto.request.SignupInput;
import com.anonymous.entity.RoleEntity;
import com.anonymous.entity.UserEntity;
import com.anonymous.exception.AppException;
import com.anonymous.repository.IRoleRepository;
import com.anonymous.repository.IUserRepository;
import com.anonymous.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {

    @Autowired
    private IUserService userService;


    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IRoleRepository roleRepository;

    private SignupInput signupInput;
    private final UserEntity userEntity = new UserEntity();
    private final RoleEntity roleEntityUser = new RoleEntity();
    private final RoleEntity roleEntityAdmin = new RoleEntity();

    @BeforeEach
    void init() {
        // Mock request data
        signupInput = SignupInput.builder()
                .username("test11112")
                .password("1111111111")
                .build();

        // Mock response data
        roleEntityUser.setId(1L);
        roleEntityUser.setCode("USER");
        roleEntityUser.setName("User");
        roleEntityAdmin.setId(2L);
        roleEntityAdmin.setCode("ADMIN");
        roleEntityAdmin.setName("Admin");

        userEntity.setUserName("test11112");
        userEntity.setPassword("1111111111");
        userEntity.setStatus(1);
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        signupInput.setRole("USER");
        Mockito.when(userRepository.findOneByUserNameAndStatus(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(null);
        Mockito.when(roleRepository.findOneByCode(ArgumentMatchers.anyString()))
                .thenReturn(roleEntityUser);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(userEntity);

        // WHEN
        UserDTO response = userService.createUser(signupInput);

        // THEN
        Assertions.assertEquals("test11112", response.getUserName());
        Assertions.assertEquals("1111111111", response.getPassword());

    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        Mockito.when(userRepository.findOneByUserNameAndStatus(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(userEntity);

        // WHEN
        AppException exception = Assertions.assertThrows(AppException.class, () -> userService.createUser(signupInput));
        Assertions.assertEquals(1003, exception.getErrorCode().getCode());
        Assertions.assertEquals("User already exist !", exception.getErrorCode().getMessage());
    }
}
