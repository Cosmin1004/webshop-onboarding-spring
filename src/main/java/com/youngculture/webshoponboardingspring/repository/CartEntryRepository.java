package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.Cart;
import com.youngculture.webshoponboardingspring.model.CartEntry;
import com.youngculture.webshoponboardingspring.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartEntryRepository extends JpaRepository<CartEntry, Long> {

    CartEntry findByCartAndProduct(Cart cart, Product product);

    List<CartEntry> findAllByCart(Cart cart, Sort sort);

}
