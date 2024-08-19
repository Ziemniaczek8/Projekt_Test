package com.example.projekt_test.appUser;


import com.example.projekt_test.mappers.AppUserMapper;
import com.example.projekt_test.registration.RegistrationRequest;
import com.example.projekt_test.registration.token.ConfirmationTokenService;
import com.example.projekt_test.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserMapper appUserMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String saveUser(RegistrationRequest request) {

        if (!ValidationUtils.validateEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (!ValidationUtils.validatePassword(request.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        boolean userExists = appUserRepository
                .findByEmail(request.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalArgumentException("User already exists");
        }

        AppUser newUser = appUserMapper.mapAppUserToDto(request);
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        appUserRepository.save(newUser);

        return "User saved";
    }

}
