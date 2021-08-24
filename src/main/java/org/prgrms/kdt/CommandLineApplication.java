package org.prgrms.kdt;

import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.voucher.VoucherProperties;
import org.prgrms.kdt.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.text.MessageFormat;

@SpringBootApplication
public class CommandLineApplication {
    static final String blacklistFileName = "customer_blacklist.csv";

    public static void main(String[] args) {
        var springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("local");
        var applicationContext = springApplication.run(args);

        Console console = new Console();
        var voucherProperties = applicationContext.getBean(VoucherProperties.class);
        console.logInfo(MessageFormat.format("Program version: {0}", voucherProperties.getVersion()));

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);

        Resource resource = applicationContext.getResource("file:" + blacklistFileName);

        new VoucherProgram(voucherService, customerService, resource, console).run();

        applicationContext.close();
    }
}
