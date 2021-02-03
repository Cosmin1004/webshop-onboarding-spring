package com.youngculture.webshoponboardingspring.controller.filter;

import com.youngculture.webshoponboardingspring.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.youngculture.webshoponboardingspring.util.Const.REGISTRATION_ERROR_1;
import static com.youngculture.webshoponboardingspring.util.Const.REGISTRATION_ERROR_2;

@WebFilter("/register")
public class RegisterFilter implements Filter {

    private final UserService userService;

    RegisterFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (userService.getByEmail(req.getParameter("email")) != null) {
            doAction(req, res, REGISTRATION_ERROR_1);
            return;
        } else if (!validatePasswordField(req.getParameter("password"))) {
            doAction(req, res, REGISTRATION_ERROR_2);
            return;
        }
        filterChain.doFilter(req, res);

    }

    private void doAction(HttpServletRequest req, HttpServletResponse res, String message)
            throws IOException, ServletException {
        req.setAttribute("message", message);
        RequestDispatcher dispatcher = req.getRequestDispatcher("home");
        dispatcher.forward(req, res);
    }

    private boolean validatePasswordField(String string) {
        Matcher matcher = Pattern.compile
                ("^(?=.*[0-9])(?=.*[a-zA-Z])\\w{8,}$")
                .matcher(string);
        return matcher.find();
    }

}
