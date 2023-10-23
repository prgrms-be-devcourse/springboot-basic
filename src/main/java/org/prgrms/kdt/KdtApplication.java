package org.prgrms.kdt;

import org.prgrms.kdt.voucher.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.prgrms.kdt.app", "org.prgrms.kdt.user", "org.prgrms.kdt.voucher"}
)
public class KdtApplication {

    public static void main(String[] args) throws IOException {
        var applicationContext = SpringApplication.run(KdtApplication.class, args);
        var voucherController = applicationContext.getBean(VoucherController.class);

        var isRepeat = true;

        while (isRepeat) {
            isRepeat = voucherController.startVoucherMenu();
        }
    }
}
