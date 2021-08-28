package org.prgms.w3d1;

import org.prgms.w3d1.io.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class W3D1Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(W3D1Application.class, args);
        Console console = new Console();
        new CommandLineApplication(console, console, applicationContext).run();
    }
}
