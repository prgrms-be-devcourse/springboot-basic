package org.prgrms.kdt;

import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class KdtApplication {

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherService = applicationContext.getBean(VoucherService.class);
        var console = new Console();

        var startMessage = "=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n";

        try {
            new CommandLineApplication(console, console, voucherService).
                    run(startMessage);
        } catch (InvalidIOMessageException e) {
            System.err.println(e.getMessage());
        }

        applicationContext.close(); //ApplicationContext을 반드시 소멸시켜야 @PreConstruct 등의 콜백이 호출됨
    }

}
