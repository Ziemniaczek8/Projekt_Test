package com.example.projekt_test.security.config;

//import com.example.projekt_test.appUser.AppUserService;

import com.example.projekt_test.appUser.AppUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@AllArgsConstructor
//@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    //    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @Bean
//    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
////                .csrf().disable()  //mozna wysylac POST request bez bycia odrzuconym // disable tylko tymczasowo
//                .authorizeHttpRequests(req -> req.requestMatchers("/", "index").permitAll() // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
//                        .requestMatchers("/api/**").hasRole("API")// kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
//                        .requestMatchers("/kartofel/**").hasRole("KARTOFEL") // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
//                        .anyRequest()  //kazdy request
//                        .authenticated())  // musi zostac autentykowany
//                .formLogin(
//                        form -> form.loginPage("/login").permitAll()
//                )
//                .logout(
//                        logout -> logout.permitAll().logoutSuccessUrl("/login?logout"));
//        return http.build();
//    }

//    @Bean
//    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(req -> req.requestMatchers("/", "index").permitAll() // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
//                       .requestMatchers("/api/**").hasRole("API")// kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
//                      .requestMatchers("/kartofel/**").hasRole("KARTOFEL") // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
//                        .anyRequest()  //kazdy request
//                       .authenticated())
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                .logout(LogoutConfigurer::permitAll);
//
//        return http.build();
//    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(req -> req.requestMatchers("/", "index.html","login","style.css","contact.html").permitAll() // kazdy request ktory przejdzie przez ten endpoint, zostanie puszczony
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
