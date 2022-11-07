package org.prgrms.springorder;

import org.prgrms.springorder.controller.CommandLineController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringOrderApplication implements CommandLineRunner {

    private final CommandLineController controller;

    public SpringOrderApplication(CommandLineController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringOrderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.run();
    }

}
