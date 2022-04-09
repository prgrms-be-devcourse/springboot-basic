package org.programmer.kdtspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtSpringBootApplication.class, args);
        applicationContext.getBean(VoucherSystem.class).run();
        applicationContext.close();
    }

}
