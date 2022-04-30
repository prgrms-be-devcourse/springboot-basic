package org.prgms.kdt;

import org.prgms.kdt.application.command.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(KdtSpringOrderApplication.class, args);
        CommandLineApplication commandLineApplication = applicationContext.getBean(CommandLineApplication.class);
        commandLineApplication.run();
    }
}
