package org.prgms.springbootbasic;

import org.prgms.springbootbasic.app.CommandLineApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("org.prgms.springbootbasic.config")
public class SpringBootBasicApplication implements CommandLineRunner {

    private final CommandLineApplication commandLineApplication;

    public SpringBootBasicApplication(CommandLineApplication commandLineApplication) {
        this.commandLineApplication = commandLineApplication;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicApplication.class, args);
    }


    @Override
    public void run(String... args) {
        this.commandLineApplication.run(true);
    }
}
