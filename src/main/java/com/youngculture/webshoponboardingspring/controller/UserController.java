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

import static com.youngculture.webshoponboardingspring.util.Const.REDIRECT_HOME;

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

        //if the user for login is admin, then skip the merge + remove
        if (!user.isAdmin()) {
            afterLogin(request, response, user);
        }

        return REDIRECT_HOME;
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("registerForm") RegisterDto registerDto,
                           HttpServletRequest request, HttpServletResponse response) {
        //validation filter in RegisterFilter

        User user = dtoConvertor.convertRegisterDtoToUser(registerDto);

        userService.saveUser(user);
        setUserSession(request, user);

        afterLogin(request, response, user);

        return REDIRECT_HOME;
    }

    @PostMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return REDIRECT_HOME;
    }

    private void afterLogin(HttpServletRequest request, HttpServletResponse response,
                            User user) {
        //merge user's cart with the anonymous cart
        cartService.mergeAnonymousCartWithUserCart(user,
                anonymousCartHandler.getAnonymousCart(request));
        //remove products from anonymous cart after they were merged
        anonymousCartHandler.removeAllCartEntryCookies(request, response);
    }

    private void setUserSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("currentSessionUser", user);
    }

}
