package com.example.kdtspringmission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringMissionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication
            .run(KdtSpringMissionApplication.class, args);

        FrontController app = ac.getBean(FrontController.class);
        app.run();

    }

}
