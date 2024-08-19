package com.example.projekt_test.registration;

import com.example.projekt_test.appUser.AppUser;
import com.example.projekt_test.appUser.AppUserRepository;
import com.example.projekt_test.appUser.AppUserRole;
import com.example.projekt_test.appUser.AppUserService;
import com.example.projekt_test.mappers.AppUserMapper;
import com.example.projekt_test.registration.token.ConfirmationToken;
import com.example.projekt_test.registration.token.ConfirmationTokenService;
import com.example.projekt_test.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserMapper appUserMapper;


    /*public String register(RegistrationRequest request) {


    }*/

//    @Transactional
//    public String confirmToken(String token) {
//        ConfirmationToken confirmationToken = confirmationTokenService
//                .
//    }
}
