package com.prgrms.vouchermanager;

import com.prgrms.vouchermanager.handler.ApplicationRunner;
import com.prgrms.vouchermanager.handler.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class VoucherManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }
}
