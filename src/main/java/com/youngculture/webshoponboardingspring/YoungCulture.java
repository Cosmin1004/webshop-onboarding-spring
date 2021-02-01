package com.youngculture.webshoponboardingspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan
@SpringBootApplication
public class YoungCulture {

    public static void main(String[] args) {
        SpringApplication.run(YoungCulture.class, args);
    }

}
