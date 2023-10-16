package org.prgrms.prgrmsspring;


import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PrgrmsSpringApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(PrgrmsSpringApplication.class,
                args);
        ApplicationController applicationController = applicationContext.getBean(
                ApplicationController.class);
        while (true) {
            applicationController.start();
        }
    }

}
