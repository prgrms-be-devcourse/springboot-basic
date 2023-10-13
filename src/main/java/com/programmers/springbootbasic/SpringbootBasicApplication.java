package com.programmers.springbootbasic;

import com.programmers.springbootbasic.config.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class SpringbootBasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicApplication.class, args);
    }
}