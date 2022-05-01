package org.prgrms.voucherapp;

import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.engine.wallet.service.WalletService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(VoucherApplication.class);
        springApplication.setAdditionalProfiles("db");
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
//        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
//        CustomerService customerService = applicationContext.getBean(CustomerService.class);
//        WalletService walletService = applicationContext.getBean(WalletService.class);
//        Console console = new Console();
//        Navigator navigator = new Navigator(console, console, voucherService, customerService, walletService);
//        navigator.run();
    }

}
