package com.anonymous.config;

import com.anonymous.filters.JwtRequestFilter;
import com.anonymous.repository.UserRepository;
import com.anonymous.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter requestFilter;
    @Autowired
    private UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/users/register",
                                "/admin/register",
                                "/login"
                        ).permitAll()
                        .requestMatchers("/api**").permitAll()
                        .anyRequest()
                        .authenticated()
                );
        http.sessionManagement(s -> s.sessionCreationPolicy(STATELESS));
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .addLogoutHandler(new HeaderWriterLogoutHandler(
                        new ClearSiteDataHeaderWriter(COOKIES, CACHE, STORAGE)))
                .logoutSuccessUrl("/login?logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
        return http.build();
    }
}
