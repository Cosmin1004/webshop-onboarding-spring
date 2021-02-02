package com.youngculture.webshoponboardingspring.controller.filter;

import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.youngculture.webshoponboardingspring.util.Const.LOGIN_ERROR;

@WebFilter("/login")
public class LoginFilter implements Filter {

    private final UserService userService;

    @Autowired
    LoginFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        User user = userService.validate(req.getParameter("email"),
                req.getParameter("password"));
        if (user == null) {
            req.setAttribute("message", LOGIN_ERROR);
            RequestDispatcher dispatcher = req.getRequestDispatcher("home");
            dispatcher.forward(req, res);
            return;
        }
        filterChain.doFilter(req, res);
    }
}

