package org.programmers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.programmers.order", "org.programmers.voucher", "org.programmers.customer"}
)
public class ProjectOrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProjectOrderApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(ProjectOrderApplication.class, args);

        logger.error("logger name -> {}", logger.getName());

    }
}
