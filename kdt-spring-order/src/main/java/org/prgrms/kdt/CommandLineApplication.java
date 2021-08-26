package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.io.console.Command;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CommandLineApplication {

    public static void main(String[] args) throws IOException {;
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
        Console console = applicationContext.getBean(Console.class);
        while (console.run() != Command.EXIT);

    }
}
