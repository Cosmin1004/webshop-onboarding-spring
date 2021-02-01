package com.youngculture.webshoponboardingspring.service.impl;

import com.youngculture.webshoponboardingspring.model.Cart;
import com.youngculture.webshoponboardingspring.model.CartEntry;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.repository.CartEntryRepository;
import com.youngculture.webshoponboardingspring.repository.CartRepository;
import com.youngculture.webshoponboardingspring.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;


    @Autowired
    CartServiceImpl(CartRepository cartRepository, CartEntryRepository cartEntryRepository) {
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    @Override
    public Cart getByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

    @Override
    public void saveOrUpdateCart(User user, CartEntry cartEntry) {
        //first check if the user has already a created cart
        Cart cartDb = cartRepository.findByUserId(user.getId());
        if (cartDb == null) {
            //create cart for user if it not exist, and add the item(cart entry)
            Cart cart = setCart(user, cartEntry);
            cartRepository.save(cart);
        } else {
            //if the user cart it's already created, add/update the items(cart entries)
            List<CartEntry> cartDbEntries = new ArrayList<>();
            CartEntry cartDbEntry = cartEntryRepository
                    .findByCartAndProduct(cartDb, cartEntry.getProduct());
            if (cartDbEntry == null) {
                cartDbEntries.add(cartEntry);
                cartDb.setCartEntries(cartDbEntries);
            } else {
                cartDbEntry.setQuantity(cartDbEntry.getQuantity() + 1);
            }
            cartRepository.save(cartDb);
        }
    }

    @Override
    public void mergeAnonymousCartWithUserCart(User user,
                                               List<CartEntry> anonymousCartEntries) {
        //if the anonymous cart is empty - nothing to merge
        if(anonymousCartEntries.isEmpty()) {
            return;
        }
        //first check if the user has already a created cart
        Cart cartDb = cartRepository.findByUserId(user.getId());
        if (cartDb == null) {
            //create cart for user if it not exist, and add the items from the anonymous cart
            Cart cart = setCart(user, anonymousCartEntries);
            cartRepository.save(cart);
        } else {
            //if the user cart it's already created, add/update the items(cart entries)
            List<CartEntry> cartDbEntries = new ArrayList<>();
            for (CartEntry cartEntry : anonymousCartEntries) {
                CartEntry cartDbEntry = cartEntryRepository
                        .findByCartAndProduct(cartDb, cartEntry.getProduct());
                if (cartDbEntry == null) {
                    cartEntry.setCart(cartDb);
                    cartDbEntries.add(cartEntry);
                    cartDb.setCartEntries(cartDbEntries);
                } else {
                    cartDbEntry.setQuantity(cartDbEntry.getQuantity()
                            + cartEntry.getQuantity());
                }
            }
            cartRepository.save(cartDb);
        }
    }

    @Override
    public List<CartEntry> getCartEntriesByCart(Cart cart) {
        return cartEntryRepository.findAllByCart(cart, Sort.by("quantity"));
    }

    //for merge (anonymous cart(many entries) with user's cart)
    private Cart setCart(User user, List<CartEntry> anonymousCartEntries) {
        Cart cart = new Cart();
        cart.setCartEntries(anonymousCartEntries);
        cart.setUser(user);
        for (CartEntry cartEntry : anonymousCartEntries) {
            cartEntry.setCart(cart);
        }
        return cart;
    }

    //for adding a product(one entry) to the cart
    private Cart setCart(User user, CartEntry cartEntry) {
        Cart cart = new Cart();
        List<CartEntry> cartEntries = new ArrayList<>();
        cartEntries.add(cartEntry);
        cartEntry.setCart(cart);
        cart.setUser(user);
        cart.setCartEntries(cartEntries);

        return cart;
    }

}
