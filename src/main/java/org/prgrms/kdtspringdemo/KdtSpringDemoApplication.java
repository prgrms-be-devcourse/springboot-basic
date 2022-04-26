package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.domain.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringDemoApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtSpringDemoApplication.class, args);
        MainController mainController = applicationContext.getBean(MainController.class);

        // App 시작
        mainController.startApp();
    }
}
