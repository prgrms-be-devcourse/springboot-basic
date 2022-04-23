package org.prgrms.springbootbasic;

import java.util.Scanner;
import org.prgrms.springbootbasic.controller.VoucherController;
import org.prgrms.springbootbasic.view.CustomerBlackList;
import org.prgrms.springbootbasic.view.ScannerView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
        var voucherController = applicationContext.getBean(VoucherController.class);
        voucherController.run();
    }

    @Bean
    public ScannerView scannerView(CustomerBlackList customerBlackList) {
        return new ScannerView(new Scanner(System.in), customerBlackList);
    }

}
