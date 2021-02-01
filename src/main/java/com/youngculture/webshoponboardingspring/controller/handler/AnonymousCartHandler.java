package com.youngculture.webshoponboardingspring.controller.handler;

import com.google.gson.Gson;
import com.youngculture.webshoponboardingspring.model.CartEntry;
import com.youngculture.webshoponboardingspring.model.CartEntryCookie;
import com.youngculture.webshoponboardingspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * necessary methods for anonymous cart (saved with cookies)
 * all CRUD operations
 */
@Component
public class AnonymousCartHandler {

    private final ProductService productService;

    @Autowired
    public AnonymousCartHandler(ProductService productService) {
        this.productService = productService;
    }

    //all items from anonymous cart
    public List<CartEntry> getAnonymousCart(HttpServletRequest request) {
        List<CartEntryCookie> cartEntriesFromCookies = getCartEntriesFromCookies(request);
        List<CartEntry> cartEntries = new ArrayList<>();

        for (CartEntryCookie cartEntryCookie : cartEntriesFromCookies) {
            CartEntry cartEntry = new CartEntry();
            cartEntry.setProduct(productService
                    .getByName(cartEntryCookie.getProductName()));
            cartEntry.setQuantity(cartEntryCookie.getQuantity());
            cartEntries.add(cartEntry);
        }
        //sort by quantity
        return cartEntries.stream()
                .sorted(Comparator.comparing(CartEntry :: getQuantity))
                .collect(Collectors.toList());
    }

    //add product to the anonymous cart
    public void saveToCookies(HttpServletRequest request,
                              HttpServletResponse response, String product) {
        Gson gson = new Gson();
        CartEntryCookie cartEntry = new CartEntryCookie();
        cartEntry.setProductName(product);

        Cookie cookie = new Cookie("cartEntry" + (int) (Math.random() * (1000 - 1)) + 1,
                URLEncoder.encode(gson.toJson(cartEntry)));
        if (cartEntryCookieExist(request, product) != null) {
            updateCartEntryCookieQuantity(request, response, product);
        } else {
            response.addCookie(cookie);
        }
    }

    //remove specific product from the anonymous cart
    public void removeCartEntryCookie(HttpServletRequest request, HttpServletResponse response,
                                      String productName) {
        Cookie cookie = getCookieByProduct(request, productName);
        if (cookie != null) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
    }

    //remove all products from the anonymous cart
    public void removeAllCartEntryCookies(HttpServletRequest request,
                                          HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().startsWith("cartEntry")) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    private void updateCartEntryCookieQuantity(HttpServletRequest request, HttpServletResponse response,
                                               String product) {
        Gson gson = new Gson();
        CartEntryCookie oldCartEntryCookie = cartEntryCookieExist(request, product);
        Cookie cookieToBeUpdated = getCookieByProduct(request, product);

        CartEntryCookie newCartEntryCookie = new CartEntryCookie();
        newCartEntryCookie.setProductName(product);
        newCartEntryCookie.setQuantity(oldCartEntryCookie.getQuantity() + 1);

        cookieToBeUpdated.setValue(
                URLEncoder.encode(gson.toJson(newCartEntryCookie)));

        response.addCookie(cookieToBeUpdated);
    }

    private CartEntryCookie cartEntryCookieExist(HttpServletRequest request,
                                                 String product) {
        List<CartEntryCookie> cartEntryCookies = getCartEntriesFromCookies(request);
        for (CartEntryCookie cartEntryCookie : cartEntryCookies) {
            if (cartEntryCookie.getProductName().equals(product)) {
                return cartEntryCookie;
            }
        }
        return null;
    }

    private List<CartEntryCookie> getCartEntriesFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<CartEntryCookie> cartEntriesFromCookies = new ArrayList<>();
        Gson gson = new Gson();
        for (Cookie cookie : cookies) {
            if (cookie.getName().startsWith("cartEntry")) {
                String jsonCookie = URLDecoder.decode(cookie.getValue());
                CartEntryCookie cartEntryCookie =
                        gson.fromJson(jsonCookie, CartEntryCookie.class);
                cartEntriesFromCookies.add(cartEntryCookie);
            }
        }
        return cartEntriesFromCookies;
    }

    private Cookie getCookieByProduct(HttpServletRequest request,
                                      String product) {
        Cookie[] cookies = request.getCookies();
        Gson gson = new Gson();
        for (Cookie cookie : cookies) {
            if (cookie.getName().startsWith("cartEntry")) {
                String jsonCookie = URLDecoder.decode(cookie.getValue());
                CartEntryCookie cartEntryCookie = gson.fromJson(jsonCookie, CartEntryCookie.class);
                if (cartEntryCookie.getProductName().equals(product)) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
