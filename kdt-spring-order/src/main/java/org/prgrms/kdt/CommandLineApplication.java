package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.io.console.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CommandLineApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication(CommandLineApplication.class);
        ConfigurableApplicationContext applicationContext = springApplication.run(args);

        Console console = applicationContext.getBean(Console.class);
        while (console.run() != Command.EXIT);
    }
}
