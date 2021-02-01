package com.youngculture.webshoponboardingspring.service;

import com.youngculture.webshoponboardingspring.model.Cart;
import com.youngculture.webshoponboardingspring.model.CartEntry;
import com.youngculture.webshoponboardingspring.model.User;

import java.util.List;

public interface CartService {

    Cart getByUserId(Long id);

    void saveOrUpdateCart(User user, CartEntry cartEntry);

    void mergeAnonymousCartWithUserCart(User user, List<CartEntry> anonymousCartEntries);

    List<CartEntry> getCartEntriesByCart(Cart cart);

}
