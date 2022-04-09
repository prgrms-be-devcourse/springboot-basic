package org.prgms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApplication {


    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);
        var app = context.getBean(CommandLineApplication.class);
        app.readBlackList("customer_blacklist.csv");
        app.execute();
    }

}
