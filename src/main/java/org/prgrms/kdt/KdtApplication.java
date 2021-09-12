package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {
    public static void main(final String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);
        final CommandLineApplication application = applicationContext.getBean(CommandLineApplication.class);
        application.run();
    }
}