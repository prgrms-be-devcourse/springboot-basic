package org.prgrms.kdt;

import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.voucher.VoucherProperties;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;

import java.text.MessageFormat;


public class CommandLineApplication {
    static final String blacklistFileName = "customer_blacklist.csv";

    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");
        applicationContext.register(AppConfiguration.class);
        applicationContext.refresh();

        var voucherProperties = applicationContext.getBean(VoucherProperties.class);
        System.out.println(MessageFormat.format("Program version: {0}", voucherProperties.getVersion()));

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);

        Resource resource = applicationContext.getResource("file:" + blacklistFileName);

        Console console = new Console();
        new VoucherProgram(voucherService, customerService, resource, console).run();
    }
}
