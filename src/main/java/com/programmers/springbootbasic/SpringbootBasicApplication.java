package com.programmers.springbootbasic;

import com.programmers.springbootbasic.common.CommonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootBasicApplication.class, args);
        var commonController = context.getBean(CommonController.class);
        commonController.run();
    }

}
