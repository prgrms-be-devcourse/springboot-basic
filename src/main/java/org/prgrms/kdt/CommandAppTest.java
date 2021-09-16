package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.springframework.boot.SpringApplication;

// @SpringBootApplication
public class CommandAppTest {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(CommandAppTest.class, args);

        applicationContext.getBean(CommandLineApplication.class).run();
    }
}