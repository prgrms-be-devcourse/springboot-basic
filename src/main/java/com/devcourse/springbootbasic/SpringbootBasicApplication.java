package com.devcourse.springbootbasic;

import com.devcourse.springbootbasic.application.controller.PlatformController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicApplication.class, args)
                .getBean(PlatformController.class)
                .run();
    }

}
