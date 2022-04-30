package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.domain.console.MainConsoleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringDemoConsoleApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtSpringDemoConsoleApplication.class, args);
        MainConsoleController mainConsoleController = applicationContext.getBean(MainConsoleController.class);

        // App 시작
        mainConsoleController.startApp();
    }
}
