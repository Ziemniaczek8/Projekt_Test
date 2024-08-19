package com.example.projekt_test.mappers;

import com.example.projekt_test.appUser.AppUser;
import com.example.projekt_test.registration.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser mapAppUserToDto(RegistrationRequest request) {
        return AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }
}
