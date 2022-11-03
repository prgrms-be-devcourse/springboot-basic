package org.programmers.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
        applicationContext.getBean(VoucherExecutor.class).run();
    }

}
