package org.prgrms.kdtspringdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringDemoApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtSpringDemoApplication.class, args);
        AppProgram appProgram = applicationContext.getBean(AppProgram.class);

        // App 시작
        appProgram.startApp();
    }
}
