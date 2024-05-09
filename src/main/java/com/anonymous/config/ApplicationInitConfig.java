package com.anonymous.config;

import com.anonymous.entity.RoleEntity;
import com.anonymous.entity.UserEntity;
import com.anonymous.repository.IRoleRepository;
import com.anonymous.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USERNAME = "admin1234";
    @NonFinal
    static final String ADMIN_PASSWORD = "admin1234";

    @Bean
    @ConditionalOnBean
    ApplicationRunner applicationRunner(IUserRepository userRepository, IRoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findOneByUserNameAndStatus(ADMIN_USERNAME, 1) == null) {
                RoleEntity roleUser = new RoleEntity();
                roleUser.setCode("USER");
                roleUser.setName("User");
                roleRepository.save(roleUser);

                RoleEntity roleAdmin = new RoleEntity();
                roleAdmin.setCode("ADMIN");
                roleAdmin.setName("Admin");
                roleAdmin = roleRepository.save(roleAdmin);

                UserEntity user = new UserEntity();
                user.setUserName(ADMIN_USERNAME);
                user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
                user.setRoles(Set.of(roleAdmin));
                userRepository.save(user);

                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
