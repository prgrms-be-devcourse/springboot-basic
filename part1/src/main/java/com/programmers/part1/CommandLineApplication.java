package com.programmers.part1;


import com.programmers.part1.ui.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(CommandLineApplication.class, args);
        Client client = applicationContext.getBean(Client.class);
        client.run();
    }
}
