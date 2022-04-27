package org.prgrms.springbootbasic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Part1Application {
    private static final Logger logger = LoggerFactory.getLogger(Part1Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Part1Application.class, args);
    }
}
