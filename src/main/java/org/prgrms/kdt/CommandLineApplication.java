package org.prgrms.kdt;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.prgrms.kdt.engine.VoucherProgram;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        Console console = new Console();

        new VoucherProgram(voucherService, console, console).run();
    }
}
