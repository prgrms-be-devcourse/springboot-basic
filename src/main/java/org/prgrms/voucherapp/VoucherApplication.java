package org.prgrms.voucherapp;

import org.prgrms.voucherapp.engine.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherApplication.class, args);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var console = new Console();
        var navigator = new Navigator(console, console, voucherService);
        navigator.run();
    }

}
