package org.programmers;

import org.programmers.console.Console;
import org.programmers.customer.service.CustomerService;
import org.programmers.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherAppAplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = new SpringApplication(VoucherAppAplication.class).run();

        var voucherService = applicationContext.getBean(VoucherService.class);
        var customerService = applicationContext.getBean(CustomerService.class);

        new VoucherProgram(voucherService, new Console(), customerService).run();
    }
}
