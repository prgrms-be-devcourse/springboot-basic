package com.programmers.kwonjoosung.springbootbasicjoosung;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "com.programmers.kwonjoosung.springbootbasicjoosung.config")
@SpringBootApplication
public class SpringBootBasicJoosungApplication implements CommandLineRunner {

    private final ApplicationLauncher applicationLauncher;

    public SpringBootBasicJoosungApplication(ApplicationLauncher applicationLauncher) {
        this.applicationLauncher = applicationLauncher;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicJoosungApplication.class, args);
    }

    @Override
    public void run(String... args) {
        applicationLauncher.run();
    }
}
