package org.prgrms.kdt;

import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherCommandLineApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherCommandLineApplication.class);
        new VoucherTester(applicationContext.getBean(VoucherService.class),
                applicationContext.getBean(VoucherRepository.class)
                ).run();
        applicationContext.close();
    }

}
