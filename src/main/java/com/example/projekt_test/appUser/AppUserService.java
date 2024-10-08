package com.example.projekt_test.appUser;


//import com.example.projekt_test.registration.token.ConfirmationToken;
//import com.example.projekt_test.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email $s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already in use");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

//        // TODO: Send Confirmation token //tworzymy token i zapisujemy go
//        String token = UUID.randomUUID().toString();  //tworzymy token
//        ConfirmationToken confirmationToken = new ConfirmationToken(token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),
//                appUser);  //token utworzony
//
//        confirmationTokenService.saveConfirmationToken(confirmationToken); //zapisujemy token
//
//        // TODO: SEND EMAIL

        return "token";
    }
}
