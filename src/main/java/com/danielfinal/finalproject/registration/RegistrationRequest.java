package com.danielfinal.finalproject.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String dni;
    private String address;
    private String zipCode;
    private String city;
    private String state;
    private String country;
}
