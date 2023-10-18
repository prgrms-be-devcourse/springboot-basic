package com.prgms.vouchermanager;

import com.prgms.vouchermanager.contorller.front.FrontController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VouchermanagerApplication {

    public static void main(String[] args) {

        ApplicationContext ac = SpringApplication.run(VouchermanagerApplication.class, args);
        FrontController frontController = ac.getBean(FrontController.class);
        frontController.run();

    }

}
