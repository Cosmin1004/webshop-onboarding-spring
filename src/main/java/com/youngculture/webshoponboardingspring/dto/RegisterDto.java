package com.youngculture.webshoponboardingspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
