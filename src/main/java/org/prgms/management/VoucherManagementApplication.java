package org.prgms.management;

import org.prgms.management.repository.VoucherRepository;
import org.prgms.management.service.ConsoleService;
import org.prgms.management.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgms.management.entity", "org.prgms.management.repository",
        "org.prgms.management.service", "org.prgms.management.configuration"})
public class VoucherManagementApplication {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(
                VoucherManagementApplication.class, args);
        var voucherService = applicationContext.getBean(VoucherService.class);
        voucherService.run();
        applicationContext.close();
    }
}
