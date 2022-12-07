package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        var springApplication = new SpringApplication(Application.class);
        springApplication.setAdditionalProfiles("release");
        springApplication.run(args);
    }
}
