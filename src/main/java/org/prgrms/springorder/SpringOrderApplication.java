package org.prgrms.springorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("org.prgrms.springorder.config")
@SpringBootApplication
public class SpringOrderApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringOrderApplication.class, args);
    }

}
