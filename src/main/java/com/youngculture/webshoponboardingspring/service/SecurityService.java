package com.youngculture.webshoponboardingspring.service;

public interface SecurityService {

    void setKey(String myKey);

    String encrypt(String strToEncrypt, String secret);

}
