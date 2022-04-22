package org.programmers.kdt.weekly;

import org.programmers.kdt.weekly.command.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringVoucherApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(SpringVoucherApplication.class, args);
        var commandLineApplication = applicationContext.getBean(CommandLineApplication.class);
        commandLineApplication.run();
    }
}