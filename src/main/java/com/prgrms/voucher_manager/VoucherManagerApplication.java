package com.prgrms.voucher_manager;

import com.prgrms.voucher_manager.io.VoucherManagerConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(VoucherManagerApplication.class, args);
//        var console = applicationContext.getBean(VoucherManagerConsole.class);
//        console.run();

        //var applicationContext = springApplication.run(args);
        //var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    }

}
