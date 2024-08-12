package com.example.projekt_test.registration;

import com.example.projekt_test.appUser.AppUser;
import com.example.projekt_test.appUser.AppUserRole;
import com.example.projekt_test.appUser.AppUserService;
import com.example.projekt_test.registration.token.ConfirmationToken;
import com.example.projekt_test.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    //rejestrowanie: sprawdzamy czy email jest valid
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException(request.getEmail() + " is not a valid email address");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

//    @Transactional
//    public String confirmToken(String token) {
//        ConfirmationToken confirmationToken = confirmationTokenService
//                .
//    }
}
