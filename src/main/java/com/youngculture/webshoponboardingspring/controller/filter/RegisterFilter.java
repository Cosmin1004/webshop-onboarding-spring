package com.youngculture.webshoponboardingspring.controller.filter;

import com.youngculture.webshoponboardingspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter("/register")
public class RegisterFilter implements Filter {

    private final UserService userService;

    @Autowired
    RegisterFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String message;

        if (userService.getByEmail(req.getParameter("email")) != null) {
            message = "Registration problem: This email is already used!";
            doAction(req, res, message);
            return;
        } else if (!validatePasswordField(req.getParameter("password"))) {
            message = "Registration problem: Password must have at least 8 characters " +
                    "(with minimum 1 digit, no special characters)!";
            doAction(req, res, message);
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
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}
