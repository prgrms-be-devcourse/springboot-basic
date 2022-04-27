package org.programmer.kdtspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringBootApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(KdtSpringBootApplication.class, args);
        applicationContext.getBean(VoucherSystem.class).run();
    }

}
