package com.prgrms.voucher_manager;

import com.prgrms.voucher_manager.io.VoucherManagerConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication(VoucherManagerApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        var applicationContext = springApplication.run(args);
        //var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var console = applicationContext.getBean(VoucherManagerConsole.class);

        console.run();
    }

}
