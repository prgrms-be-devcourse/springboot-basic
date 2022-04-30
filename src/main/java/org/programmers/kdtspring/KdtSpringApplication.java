package org.programmers.kdtspring;

import org.programmers.kdtspring.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(KdtSpringApplication.class, args);
        ac.getBean(VoucherController.class).run();
    }
}