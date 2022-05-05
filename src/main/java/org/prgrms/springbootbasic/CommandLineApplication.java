package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.engine.VoucherManager;
import org.prgrms.springbootbasic.engine.service.CustomerService;
import org.prgrms.springbootbasic.engine.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommandLineAppConfiguration.class);
        VoucherProperties voucherProperties = applicationContext.getBean(VoucherProperties.class);
        logger.info("environment => {}", voucherProperties.getEnvironment());
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        Console console = new Console();

        new VoucherManager(voucherService,customerService, console, console, logger).run();

    }
}
