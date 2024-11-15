package com.naves.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/h2-console/**").permitAll()  // Permite acceso sin autenticación a la consola H2
                    .anyRequest().authenticated()  // Requiere autenticación para cualquier otra solicitud
            )
            .formLogin(formLogin -> formLogin
                .permitAll()  // Permite acceso a la página de login sin autenticación
            )
            .csrf(csrf -> csrf
                .disable()  // Desactiva CSRF para permitir la consola H2
            );

        return http.build(); // Retorna la configuración de seguridad
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usamos BCryptPasswordEncoder para encriptar las contraseñas
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> User.builder()
                .username("Naves")
                .password(passwordEncoder().encode("pruebaNAVES"))
                .roles("USER")
                .build();  // Construye el usuario con su nombre, contraseña y roles
    }
}
