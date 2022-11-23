package org.prgrms.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication
                .run(SpringbootBasicApplication.class, args);

        Console console = configurableApplicationContext.getBean(Console.class);
        console.run();
    }

}
