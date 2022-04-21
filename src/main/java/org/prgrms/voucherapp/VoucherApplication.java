package org.prgrms.voucherapp;

import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherApplication.class, args);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var customerService = applicationContext.getBean(CustomerService.class);
        var console = new Console();
        var navigator = new Navigator(console, console, voucherService, customerService);
        navigator.run();
    }

}
