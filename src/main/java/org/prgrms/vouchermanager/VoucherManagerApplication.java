package org.prgrms.vouchermanager;

import org.prgrms.vouchermanager.voucher.domain.VoucherType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagerApplication {
    public static void main(String[] args) {

        System.out.println(VoucherType.valueOf("FIXED"));

        SpringApplication.run(VoucherManagerApplication.class, args);
    }
}
