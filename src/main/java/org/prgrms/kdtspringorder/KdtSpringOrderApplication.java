package org.prgrms.kdtspringorder;

import org.prgrms.kdtspringorder.config.YmlPropertiesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties({YmlPropertiesLoader.class})
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtSpringOrderApplication.class, args);
        App app = applicationContext.getBean(App.class);
        app.run();
    }

}
