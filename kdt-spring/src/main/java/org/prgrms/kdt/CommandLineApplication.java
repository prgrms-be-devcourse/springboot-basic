package org.prgrms.kdt;

import org.prgrms.kdt.management.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CommandLineApplication {

    public static void main(String[] args) throws IOException {;
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
        Console console = applicationContext.getBean(Console.class);
        while (true) {
            console.run();
        }

    }
}

