package com.prgrms.voucher_manager;

import com.prgrms.voucher_manager.configuration.AppConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) throws IOException {

        var applicationContext = SpringApplication.run(VoucherManagerApplication.class, args);

        //var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var console = applicationContext.getBean(VoucherManagerConsole.class);

        console.run();
    }

}
