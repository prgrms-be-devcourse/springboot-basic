package org.prgrms.kdt;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.service.customer.CustomerService;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        Input input = new InputConsole();
        Output output = new OutputConsole();
        new CommandLineRunner(input, output, voucherService, customerService).run();
    }
}
