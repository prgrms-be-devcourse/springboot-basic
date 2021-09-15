package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.prgrms.kdt.*"}
)
@EnableConfigurationProperties
public class Week3Application {

    public static void main(String[] args) throws IOException {
       SpringApplication.run(Week3Application.class, args);
    }
}
