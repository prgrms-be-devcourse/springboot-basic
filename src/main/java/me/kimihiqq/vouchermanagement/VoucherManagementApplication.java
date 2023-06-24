package me.kimihiqq.vouchermanagement;

import me.kimihiqq.vouchermanagement.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class VoucherManagementApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(VoucherManagementApplication.class, args);
        applicationContext.getBean(VoucherController.class).run();
    }
}

