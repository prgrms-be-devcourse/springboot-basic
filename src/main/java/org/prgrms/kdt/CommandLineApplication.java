package org.prgrms.kdt;

import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    static final String filePath = "./voucher.csv";

    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        Console console = new Console();

        new VoucherProgram(voucherService, console, console, filePath).run();
    }
}
