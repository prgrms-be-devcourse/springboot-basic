package org.programmers;

import org.programmers.console.Console;
import org.programmers.customer.service.BlackListCustomerService;
import org.programmers.customer.service.CustomerService;
import org.programmers.voucher.service.VoucherJdbcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherAppAplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = new SpringApplication(VoucherAppAplication.class).run();

        var voucherService = applicationContext.getBean(VoucherJdbcService.class);
        var blackListCustomerService = applicationContext.getBean(BlackListCustomerService.class);
        var customerService = applicationContext.getBean(CustomerService.class);
        var console = applicationContext.getBean(Console.class);

        new VoucherProgram(voucherService, console, blackListCustomerService,customerService).run();
        applicationContext.close();
    }
}
