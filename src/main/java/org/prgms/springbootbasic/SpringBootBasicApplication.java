package org.prgms.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("org.prgms.springbootbasic.config")
public class SpringBootBasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicApplication.class, args);
    }
}
