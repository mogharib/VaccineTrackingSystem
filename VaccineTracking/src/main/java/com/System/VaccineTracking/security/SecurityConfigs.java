package com.System.VaccineTracking.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

   @Configuration
    @EnableWebSecurity
    @AllArgsConstructor
    public class SecurityConfigs {
        private final CustomUserDetails userDetails;
        private JwtAuthEntryPoint authEntryPoint;

       @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           http
               .httpBasic()
               .and()
               .authorizeRequests()
               .mvcMatchers("/auth").authenticated()
               .mvcMatchers("/register").permitAll()
               .mvcMatchers("/api/admin/**").hasAuthority ("ADMIN")
               .and()
               .csrf().disable()
               .headers().frameOptions().disable();
           return http.build();
       }


       @Bean
       public AuthenticationManager authenticationManager(
           AuthenticationConfiguration authenticationConfiguration) throws Exception {
           return authenticationConfiguration.getAuthenticationManager();
       }

       @Bean
       PasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
       }

       @Bean
       public  JWTAuthenticationFilter jwtAuthenticationFilter() {
           return new JWTAuthenticationFilter();
       }

}