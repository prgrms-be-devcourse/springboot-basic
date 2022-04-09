package org.prgrms.part1;

import org.prgrms.part1.engine.VoucherManager;
import org.prgrms.part1.engine.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(CommandLineAppConfiguration.class);
        var console = new Console();
        var voucherService = applicationContext.getBean(VoucherService.class);

        new VoucherManager(voucherService, console, console, logger).run();

    }
}
