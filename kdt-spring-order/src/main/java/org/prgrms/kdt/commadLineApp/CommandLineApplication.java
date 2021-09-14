package org.prgrms.kdt.commadLineApp;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;

@ComponentScan(basePackages = {"org.prgrms.kdt.*"})
public class CommandLineApplication {

   // private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public static void main(String[] args) throws IOException {
        var appConfiguration = new AnnotationConfigApplicationContext();
        appConfiguration.register(AppConfiguration.class);
        var environment = appConfiguration.getEnvironment();
        environment.setActiveProfiles("jdbc");
//        environment.setActiveProfiles("local");
        appConfiguration.refresh();
        File blackList = appConfiguration.getResource("customer_blacklist.csv").getFile();
        var voucherService = appConfiguration.getBean(VoucherService.class);
        var customerService = appConfiguration.getBean(CustomerService.class);
        new CommandLineController(voucherService, customerService).startProgram(blackList);
    }
}
