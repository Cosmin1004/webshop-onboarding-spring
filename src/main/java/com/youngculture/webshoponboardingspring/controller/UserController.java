package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.controller.handler.AnonymousCartHandler;
import com.youngculture.webshoponboardingspring.dto.LoginDto;
import com.youngculture.webshoponboardingspring.dto.RegisterDto;
import com.youngculture.webshoponboardingspring.dto.convertor.DtoConvertor;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.CartService;
import com.youngculture.webshoponboardingspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;
    private final DtoConvertor dtoConvertor;
    private final CartService cartService;
    private final AnonymousCartHandler anonymousCartHandler;

    @Autowired
    public UserController(UserService userService, DtoConvertor dtoConvertor,
                          CartService cartService, AnonymousCartHandler anonymousCartHandler) {
        this.userService = userService;
        this.dtoConvertor = dtoConvertor;
        this.cartService = cartService;
        this.anonymousCartHandler = anonymousCartHandler;
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("loginForm") LoginDto loginDto,
                        HttpServletRequest request, HttpServletResponse response) {

        //validation filter in LoginFilter

        User user = userService.getByEmail(loginDto.getEmail());
        setUserSession(request, user);

        //merge user's cart with the anonymous cart
        cartService.mergeAnonymousCartWithUserCart(user,
                anonymousCartHandler.getAnonymousCart(request));
        //remove products from anonymous cart after they were merged
        anonymousCartHandler.removeAllCartEntryCookies(request, response);

        return "redirect:/home";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("registerForm") RegisterDto registerDto,
                           HttpServletRequest request) {
        User user = dtoConvertor.convertRegisterDtoToUser(registerDto);
        userService.saveUser(user);
        setUserSession(request, user);

        return "redirect:/home";
    }

    @PostMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    private void setUserSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("currentSessionUser", user);
    }

}
