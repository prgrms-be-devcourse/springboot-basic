package com.programmers.springbootbasic;

import com.programmers.springbootbasic.common.handler.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootBasicApplication.class, args);
        CommandHandler commandHandler = context.getBean(CommandHandler.class);
        commandHandler.run();
    }

}
