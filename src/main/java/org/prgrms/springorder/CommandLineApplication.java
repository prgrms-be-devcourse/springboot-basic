package org.prgrms.springorder;

import org.prgrms.springorder.controller.CommandLineController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

    private final CommandLineController controller;

    public CommandLineApplication(CommandLineController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandLineApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.run();
    }

}
