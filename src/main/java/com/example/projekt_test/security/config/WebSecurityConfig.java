package com.example.projekt_test.security.config;


import com.example.projekt_test.jwt.JwtConfig;
import com.example.projekt_test.jwt.JwtTokenVerifier;
import com.example.projekt_test.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        AuthenticationManager authenticationManager = authConfig.getAuthenticationManager();
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager, secretKey, jwtConfig))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUsernameAndPasswordAuthenticationFilter.class)

                .authorizeHttpRequests(req -> req.requestMatchers("/", "index.html", "login", "login/**").permitAll() // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
                        .requestMatchers("/api/**").hasRole("API")// kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
                        .requestMatchers("/kartofel/**").hasRole("KARTOFEL") // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
                        .anyRequest()  //kazdy request
                        .authenticated());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails api = User.builder()
                .username("api")
                .password(bCryptPasswordEncoder.encode("123456"))
                .roles("API")
                .build();

        UserDetails kartofel = User.builder()
                .username("kartofel")
                .password(bCryptPasswordEncoder.encode("123456"))
                .roles("KARTOFEL")
                .build();

        return new InMemoryUserDetailsManager(api, kartofel);
    }

}
