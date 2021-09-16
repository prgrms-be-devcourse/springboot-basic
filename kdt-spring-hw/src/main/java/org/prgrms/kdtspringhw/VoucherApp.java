package org.prgrms.kdtspringhw;

import org.prgrms.kdtspringhw.blacklist.BlackListService;
import org.prgrms.kdtspringhw.voucher.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApp {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = new SpringApplication(VoucherApp.class).run();
        var voucherService = applicationContext.getBean(VoucherService.class);
        var blackListService = applicationContext.getBean(BlackListService.class);
        new CommandLineApplication(applicationContext, voucherService, blackListService).run();
    }
}
