package com.prgms.kdtspringorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.prgms.kdtspringorder.adapter.controller.CustomerController;
import com.prgms.kdtspringorder.adapter.controller.VoucherController;

@SpringBootApplication
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KdtSpringOrderApplication.class);
        springApplication.setAdditionalProfiles("dev");
        ConfigurableApplicationContext applicationContext = springApplication.run(args);

        CustomerController customerController = applicationContext.getBean(CustomerController.class);
        VoucherController voucherController = applicationContext.getBean(VoucherController.class);
        new CommandLineApplication(customerController, voucherController).run();

        applicationContext.close();
    }

}
