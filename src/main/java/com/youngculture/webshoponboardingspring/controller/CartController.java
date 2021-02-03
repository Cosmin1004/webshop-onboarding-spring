package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.controller.handler.AnonymousCartHandler;
import com.youngculture.webshoponboardingspring.model.CartEntry;
import com.youngculture.webshoponboardingspring.model.Product;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.CartService;
import com.youngculture.webshoponboardingspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final AnonymousCartHandler anonymousCartHandler;

    @Autowired
    CartController(CartService cartService, ProductService productService,
                   AnonymousCartHandler anonymousCartHandler) {
        this.cartService = cartService;
        this.productService = productService;
        this.anonymousCartHandler = anonymousCartHandler;
    }

    @ModelAttribute("user")
    public User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("currentSessionUser");
    }

    @PostMapping(value = "/addToCart")
    public @ResponseBody
    String addProductToCart(@RequestParam("productName") String productName,
                            @ModelAttribute("user") User user,
                            HttpServletRequest request, HttpServletResponse response) {
        String responseMessage = null;
        if (user != null) {
            // add/update user cart
            CartEntry cartEntry = setCartEntry(user, productName);
            cartService.saveOrUpdateCart(user, cartEntry);
            responseMessage = "The product \"" + productName + "\" has been added to your cart.";
        } else {
            // add/update anonymous cart(from cookies)
            anonymousCartHandler.saveToCookies(request, response, productName);
            responseMessage = "The product \"" + productName + "\" has been added to the cart. " +
                    "Be aware that you are not logged in.";
        }
        return responseMessage;

    }

    @GetMapping(value = "/cart")
    public @ResponseBody
    List<CartEntry> getCart(@ModelAttribute("user") User user,
                            HttpServletRequest request) {
        List<CartEntry> cartEntries;
        if (user != null) {
            cartEntries = cartService.getCartEntriesByCart
                    (cartService.getCartByUserId(user.getId()));
        } else {
            cartEntries = anonymousCartHandler.getAnonymousCart(request);
        }

        return cartEntries;
    }

    @GetMapping("/cartCount")
    public @ResponseBody
    String getCartCount(@ModelAttribute("user") User user,
                        HttpServletRequest request) {
        Integer count;
        if (user != null) {
            count = cartService.getCartEntriesByCart
                    (cartService.getCartByUserId(user.getId())).stream()
                    .map(CartEntry::getQuantity).mapToInt(Integer::intValue).sum();
        } else {
            count = anonymousCartHandler.getAnonymousCart(request).stream()
                    .map(CartEntry::getQuantity).mapToInt(Integer::intValue).sum();
        }
        return String.valueOf(count);
    }

    @DeleteMapping(value = "/removeFromCart")
    public @ResponseBody
    String removeItemFromCart(@RequestParam("productName") String productName,
                              @ModelAttribute("user") User user,
                              HttpServletRequest request, HttpServletResponse response) {
        if (user != null) {
            cartService.removeCartEntryByCartAndProduct(cartService.getCartByUserId(user.getId()),
                    productService.getByName(productName));
        } else {
            anonymousCartHandler.removeCartEntryCookie(request, response, productName);
        }
        return "The product \"" + productName + "\" has been removed from your cart.";
    }

    @DeleteMapping(value = "/removeAll")
    public void removeAllFromCart(@ModelAttribute("user") User user) {
        cartService.removeAllCartEntriesByCart(cartService.getCartByUserId(user.getId()));
    }

    private CartEntry setCartEntry(User user, String productName) {
        CartEntry cartEntry = new CartEntry();
        Product product = productService.getByName(productName);
        cartEntry.setCart(cartService.getCartByUserId(user.getId()));
        cartEntry.setProduct(product);

        return cartEntry;
    }

}
