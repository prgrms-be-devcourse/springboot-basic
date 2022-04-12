package org.prgms.kdt;

import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.CommandLineApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(KdtSpringOrderApplication.class, args);
        CommandLineApplication commandLineApplication = applicationContext.getBean(CommandLineApplication.class);
        commandLineApplication.run();
    }

}
