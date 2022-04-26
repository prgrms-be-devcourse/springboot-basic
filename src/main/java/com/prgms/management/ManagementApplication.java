package com.prgms.management;

//import com.prgms.management.command.CommandLineApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ManagementApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ManagementApplication.class, args);
//        applicationContext.getBean(CommandLineApplication.class).run();
    }
}
