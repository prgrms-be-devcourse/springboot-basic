package org.prgrms.springbootbasic;

import java.util.Scanner;
import org.prgrms.springbootbasic.view.CustomerBlackList;
import org.prgrms.springbootbasic.view.ScannerView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicApplication.class, args);
    }

    @Bean
    @Profile("console")
    public ScannerView scannerView(CustomerBlackList customerBlackList) {
        return new ScannerView(new Scanner(System.in), customerBlackList);
    }
}
