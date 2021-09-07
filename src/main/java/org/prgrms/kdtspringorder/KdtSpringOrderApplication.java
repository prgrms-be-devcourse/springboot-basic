package org.prgrms.kdtspringorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtSpringOrderApplication.class, args);
        App app = applicationContext.getBean(App.class);
        app.run();
    }

}
