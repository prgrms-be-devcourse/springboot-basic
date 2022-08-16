package com.prgrms.kdt.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("jdbc")
@SpringBootApplication
public class SpringBootBasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicApplication.class, args);
    }
}
