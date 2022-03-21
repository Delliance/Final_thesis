package com.danielfinal.finalproject.registration;

import com.danielfinal.finalproject.entities.User;
import com.danielfinal.finalproject.enums.UserRole;
import com.danielfinal.finalproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public String register(RegistrationRequest request) {

        String token = userService.singUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getUsername(),
                        request.getPassword(),
                        request.getDni(),
                        request.getAddress(),
                        request.getZipCode(),
                        request.getCity(),
                        request.getState(),
                        request.getCountry(),
                        UserRole.USER
                )
        );

        return "works";
    }
}
