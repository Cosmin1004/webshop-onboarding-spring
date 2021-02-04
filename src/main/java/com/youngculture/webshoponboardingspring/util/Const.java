package com.youngculture.webshoponboardingspring.util;

public final class Const {

    //error messages
    public static final String LOGIN_ERROR = "Login problem: Invalid email or password!";
    public static final String REGISTRATION_ERROR_1 = "Registration problem: This email is already used!";
    public static final String REGISTRATION_ERROR_2 = "Registration problem: Password must have at least 8 characters " +
            "(with minimum 1 digit, no special characters)!";

    //for views(jsp pages)
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String HOME = "home";
    public static final String ORDER = "order";
    public static final String ADMIN = "admin";

    //actions for admin
    public static final String CONFIRM = "Confirm";
    public static final String DECLINE = "Decline";

}
