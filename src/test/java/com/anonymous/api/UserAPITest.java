package com.anonymous.api;

import com.anonymous.dto.RoleDTO;
import com.anonymous.dto.UserDTO;
import com.anonymous.dto.request.SignupInput;
import com.anonymous.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc // Create a mock request
@TestPropertySource("/test.properties")
class UserAPITest {

    @Autowired
    private MockMvc mockMvc; // Mock request

    @MockBean
    private IUserService userService;

    private SignupInput signupInput;
    private final UserDTO userDTO = new UserDTO();
    private final RoleDTO roleDTOUser = new RoleDTO();
    private final RoleDTO roleDTOAdmin = new RoleDTO();

    @BeforeEach
    void init() {
        // Mock request data
        signupInput = SignupInput.builder()
                .username("test11112")
                .password("1111111111")
                .build();

        // Mock response data
        roleDTOUser.setId(1L);
        roleDTOUser.setCode("USER");
        roleDTOUser.setName("User");
        roleDTOAdmin.setId(2L);
        roleDTOAdmin.setCode("ADMIN");
        roleDTOAdmin.setName("Admin");

        userDTO.setUserName("test11112");
        userDTO.setPassword("1111111111");
        userDTO.setStatus(1);
    }

    @Test
    void createAccountUser_validUserName_success() throws Exception {
        // GIVEN
        userDTO.setRoles(Set.of(roleDTOUser));
        signupInput.setRole("USER");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(signupInput);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userDTO);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.userName").value("test11112"));

    }

    @Test
    void createAccountUser_invalidUserName_fail() throws Exception {
        // GIVEN
        userDTO.setRoles(Set.of(roleDTOUser));
        signupInput.setRole("USER");
        signupInput.setUsername("test");
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(signupInput);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userDTO);


        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 8 characters !"));
    }

}
