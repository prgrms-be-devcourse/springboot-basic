package com.prgmrs.voucher;

import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.setting.VoucherProperties;
import com.prgmrs.voucher.view.ConsoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({BlacklistProperties.class, VoucherProperties.class})
public class VoucherApplication implements CommandLineRunner {

    private final ConsoleView consoleView;

    @Autowired
    public VoucherApplication(ConsoleView console) {
        this.consoleView = console;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        consoleView.run();
    }
}
