package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.prgrms.kdt.customer.domain.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

@SpringBootApplication
public class CommandAppTest {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(CommandAppTest.class, args);

        applicationContext.getBean(CommandLineApplication.class).run();
    }
}