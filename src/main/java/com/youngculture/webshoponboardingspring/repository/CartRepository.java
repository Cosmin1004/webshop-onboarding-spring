package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long id);

}
