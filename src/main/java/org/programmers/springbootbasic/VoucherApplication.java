package org.programmers.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherApplication.class, args);
        var commandLineApplication = applicationContext.getBean(CommandLineApplication.class);
        commandLineApplication.run();
    }
}
