package com.youngculture.webshoponboardingspring.dto.convertor;

import com.youngculture.webshoponboardingspring.dto.RegisterDto;
import com.youngculture.webshoponboardingspring.model.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConvertor {

    public User convertRegisterDtoToUser(RegisterDto registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(registerDto.getPassword());
        user.setAdmin(false);

        return user;
    }

}
