package com.example.springbootbasic;

import com.example.springbootbasic.config.AppConfiguration;
import com.example.springbootbasic.console.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(AppConfiguration.class)
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
        Console console = ac.getBean(Console.class);


        while (true) {
            console.process();
        }
    }
}
