package org.prgrms.springorder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("org.prgrms.springorder.config")
@SpringBootApplication
public class SpringOrderApplication implements CommandLineRunner {

    private final CommandLineApplication commandLineApplication;

    public SpringOrderApplication(
        CommandLineApplication commandLineApplication) {
        this.commandLineApplication = commandLineApplication;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringOrderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        commandLineApplication.run(args);
    }

}
