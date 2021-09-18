package org.prgrms.devcourse;

import org.prgrms.devcourse.blackuser.BlackUserService;
import org.prgrms.devcourse.customer.CustomerServiceImpl;
import org.prgrms.devcourse.voucher.VoucherService;
import org.prgrms.devcourse.ui.CommandLineInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.devcourse.customer", "org.prgrms.devcourse.blackuser", "org.prgrms.devcourse.voucher", "org.prgrms.devcourse.configuration"})
public class DevcourseSpringMissionApplication {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(DevcourseSpringMissionApplication.class, args);
        new VoucherProgram(applicationContext.getBean(VoucherService.class),
                applicationContext.getBean(BlackUserService.class),
                applicationContext.getBean(CustomerServiceImpl.class),
                new CommandLineInterface())
                .run();
        applicationContext.close();
    }
}
