package org.programmers.springbootbasic;

import org.programmers.springbootbasic.core.CommandLineApplication;
import org.programmers.springbootbasic.core.io.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
        Console console = new Console();
        new CommandLineApplication(console, applicationContext).run();
    }
}
