package org.programmers.kdtspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(VoucherManagement.class, args);
        ac.getBean(VoucherManagement.class).run();
    }

}
