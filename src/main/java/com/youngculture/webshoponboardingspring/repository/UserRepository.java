package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByAdmin(boolean admin);

}
