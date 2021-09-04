package org.prgrms.kdt.devcourse;

import org.prgrms.kdt.devcourse.customer.CustomerService;
import org.prgrms.kdt.devcourse.io.Console;
import org.prgrms.kdt.devcourse.voucher.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DevCourseApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AppConfiguration.class, args);

        VoucherService voucherService = context.getBean(VoucherService.class);
        CustomerService customerService = context.getBean(CustomerService.class);

        new CommandLineApplication(voucherService,customerService).run();

        context.close();

    }

}
