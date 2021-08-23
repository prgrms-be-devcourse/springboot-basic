package com.prgrm.kdt;

import com.prgrm.kdt.customer.application.CustomerService;
import com.prgrm.kdt.customer.domain.CustomerProperties;
import com.prgrm.kdt.customer.presentation.CustomerController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class CommandLineApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();

//        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
//        new VoucherController(voucherService).run();
        CustomerProperties customerProperties = applicationContext.getBean(CustomerProperties.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        new CustomerController(customerService, customerProperties).run();
    }
}
