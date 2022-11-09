package com.example.springbootbasic;

import com.example.springbootbasic.config.AppConfiguration;
import com.example.springbootbasic.console.ResponseType;
import com.example.springbootbasic.console.console.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import static com.example.springbootbasic.console.ResponseType.END;
import static com.example.springbootbasic.console.ResponseType.SUCCESS;

@SpringBootApplication
@EnableConfigurationProperties(AppConfiguration.class)
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
        Console console = ac.getBean(Console.class);


        ResponseType status = SUCCESS;
        while (status != END) {
            console.requestMainMenus();
            status = console.request().getStatus();
        }
    }
}
