package org.prgms.management;

import org.prgms.management.service.ManagementService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgms.management.entity", "org.prgms.management.repository",
        "org.prgms.management.service", "org.prgms.management.io"})
public class VoucherManagementApplication {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(
                VoucherManagementApplication.class, args);
        var voucherService = applicationContext.getBean(ManagementService.class);
        voucherService.run();
        applicationContext.close();
    }
}
