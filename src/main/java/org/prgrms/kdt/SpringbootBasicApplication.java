package org.prgrms.kdt;

import org.prgrms.kdt.forward.IOController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication implements CommandLineRunner {

    private final IOController ioController;

    public SpringbootBasicApplication(IOController ioController) {
        this.ioController = ioController;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ioController.run();
    }
}
