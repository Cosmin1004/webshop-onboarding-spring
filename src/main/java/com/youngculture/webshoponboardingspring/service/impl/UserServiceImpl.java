package com.youngculture.webshoponboardingspring.service.impl;

import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.repository.UserRepository;
import com.youngculture.webshoponboardingspring.service.SecurityService;
import com.youngculture.webshoponboardingspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final String secretKey = "secretKey";


    @Autowired
    UserServiceImpl(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public void saveUser(User user) {
        String password = user.getPassword();
        String encryptPwd = securityService.encrypt(password, secretKey);
        user.setPassword(encryptPwd);

        userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User validate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword()
                .equals(securityService.encrypt(password, secretKey))) {
            return user;
        }
        return null;
    }

}
