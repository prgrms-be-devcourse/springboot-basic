package com.programmers.kwonjoosung.springbootbasicjoosung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "com.programmers.kwonjoosung.springbootbasicjoosung.config")
@SpringBootApplication
public class SpringBootBasicJoosungApplication  {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicJoosungApplication.class, args);
    }

}
