package org.prgrms.kdt;

import org.prgrms.kdt.customer.CustomerServiceImpl;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import java.text.MessageFormat;

@SpringBootApplication
public class CommandLineApplication {
    static final String blacklistFileName = "customer_blacklist.csv";

    public static void main(String[] args) {
        var springApplication = new SpringApplication(CommandLineApplication.class);

        Console console = new Console();

        springApplication.setAdditionalProfiles("local");
        var applicationContext = springApplication.run(args);

        var environment = applicationContext.getEnvironment();
        var version = environment.getProperty("version");
        console.logInfo(MessageFormat.format("Program version: {0}", version));

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerServiceImpl customerService = applicationContext.getBean(CustomerServiceImpl.class);

        Resource resource = applicationContext.getResource("file:" + blacklistFileName);

        new VoucherProgram(voucherService, customerService, resource, console).run();

        applicationContext.close();
    }
}
