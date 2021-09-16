package org.prgrms.devcourse;

import org.prgrms.devcourse.service.BlackUserService;
import org.prgrms.devcourse.service.VoucherService;
import org.prgrms.devcourse.ui.CommandLineInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.devcourse.domain", "org.prgrms.devcourse.repository", "org.prgrms.devcourse.service"})
public class DevcourseSpringMissionApplication {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(DevcourseSpringMissionApplication.class, args);
        new VoucherProgram(applicationContext.getBean(VoucherService.class),
                applicationContext.getBean(BlackUserService.class),
                new CommandLineInterface())
                .run();
        applicationContext.close();
    }
}
