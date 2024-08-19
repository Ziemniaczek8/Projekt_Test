package com.example.projekt_test.controller;

import com.example.projekt_test.appUser.AppUserService;
import com.example.projekt_test.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/registration")
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return appUserService.saveUser(request);
    }
}
