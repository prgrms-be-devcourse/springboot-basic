package org.prgrms.kdt.shop;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.shop.io.Console;
import org.prgrms.kdt.shop.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
