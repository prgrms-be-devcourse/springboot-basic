package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandAppTest {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(CommandAppTest.class, args);

        applicationContext.getBean(CommandLineApplication.class).run();
    }
}