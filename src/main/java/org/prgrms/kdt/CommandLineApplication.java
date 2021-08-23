package org.prgrms.kdt;

import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

public class CommandLineApplication {
    static final String filePath = "./voucher.csv";
    static final String blacklistFileName = "customer_blacklist.csv";

    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);

        //Resource resource = applicationContext.getResource("file:" + blacklistFileName);
        Resource resource = applicationContext.getResource("file:customer_blacklist.csv");

        Console console = new Console();
        new VoucherProgram(voucherService, customerService, resource, console, filePath).run();
    }
}
