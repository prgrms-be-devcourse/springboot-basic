package com.example.voucherproject;

import com.example.voucherproject.common.config.AppConfig;
import com.example.voucherproject.common.config.UserServiceConfig;
import com.example.voucherproject.common.config.VoucherServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({AppConfig.class, UserServiceConfig.class, VoucherServiceConfig.class})
@SpringBootApplication
public class VoucherProjectApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherProjectApplication.class, args);
        applicationContext.getBean(AllService.class).run();
    }
}
