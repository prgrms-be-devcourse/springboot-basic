package me.kimihiqq.vouchermanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class VoucherManagementApplication {

//    public static void main(String[] args) {
//        log.info("Starting Voucher Management Application");
//        ApplicationContext applicationContext = SpringApplication.run(VoucherManagementApplication.class, args);
//        applicationContext.getBean(VoucherConsoleController.class).run();
//        log.info("Voucher Management Application has stopped");
//    }
    public static void main(String[] args) {
        log.info("Starting Voucher Management Application");
        SpringApplication.run(VoucherManagementApplication.class, args);
        log.info("Voucher Management Application has started");
    }
}

