package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication {
    static final String blacklistFileName = "blacklist/customer_blacklist.csv";

    public static void main(String[] args) {
        var springApplication = new SpringApplication(CommandLineApplication.class);
        springApplication.setAdditionalProfiles("prod");

        var applicationContext = springApplication.run(args);

        applicationContext.getBean(VoucherProgram.class).run();
        applicationContext.close();

        //        Console console = new Console();
//        var environment = applicationContext.getEnvironment();
//        var version = environment.getProperty("version");
//        console.logInfo(MessageFormat.format("Program version: {0}", version));
//
//        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
//        CustomerServiceImpl customerService = applicationContext.getBean(CustomerServiceImpl.class);
//
//        Resource resource = applicationContext.getResource("file:" + blacklistFileName);
//
//        new VoucherProgram(voucherService, customerService, resource, console).run();
    }
}
