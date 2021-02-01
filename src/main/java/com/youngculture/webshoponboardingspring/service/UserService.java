package com.youngculture.webshoponboardingspring.service;

import com.youngculture.webshoponboardingspring.model.User;

public interface UserService {

    void saveUser(User user);

    User getByEmail(String email);

    User validate(String email, String password);

}



