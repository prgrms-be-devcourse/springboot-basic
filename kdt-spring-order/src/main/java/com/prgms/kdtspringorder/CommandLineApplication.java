package com.prgms.kdtspringorder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.prgms.kdtspringorder.adapter.controller.CustomerController;
import com.prgms.kdtspringorder.adapter.controller.VoucherController;
import com.prgms.kdtspringorder.application.CustomerService;
import com.prgms.kdtspringorder.application.VoucherService;
import com.prgms.kdtspringorder.config.AppConfig;

public class CommandLineApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        CustomerController customerController = new CustomerController(
            applicationContext.getBean(CustomerService.class));
        customerController.findAllBlacklist();

        VoucherController voucherController = new VoucherController(applicationContext.getBean(VoucherService.class));
        voucherController.manageVouchers();

        applicationContext.close();
    }
}
