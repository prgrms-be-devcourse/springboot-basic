package com.example.kdtspringmission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringMissionApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(
            KdtSpringMissionApplication.class);
        springApplication.setAdditionalProfiles("dev");
        ConfigurableApplicationContext ac = springApplication.run(args);

        CommandLineApplication app = ac.getBean(CommandLineApplication.class);
        app.run();
    }

}
