package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.customer.controller.CustomerController;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        // app context에 bean 등록
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        new CustomerController(applicationContext.getBean(CustomerService.class));
        VoucherController voucherController = new VoucherController(applicationContext.getBean(VoucherService.class));
        voucherController.run();
        applicationContext.close();
    }
}
