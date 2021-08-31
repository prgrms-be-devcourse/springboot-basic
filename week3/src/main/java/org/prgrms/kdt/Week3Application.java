package org.prgrms.kdt;

import org.prgrms.kdt.io.CommandLineInterface;
import org.prgrms.kdt.voucher.VoucherProgram;
import org.prgrms.kdt.blacklist.BlackListrService;
import org.prgrms.kdt.voucher.service.MemoryVoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.prgrms.kdt.*"}
)
@EnableConfigurationProperties
public class Week3Application {

    public static void main(String[] args) throws IOException {
        var applicationContext = SpringApplication.run(Week3Application.class, args);

        new VoucherProgram(
                applicationContext.getBean(BlackListrService.class),
                applicationContext.getBean(MemoryVoucherService.class),
                applicationContext.getBean(CommandLineInterface.class)
        ).runProgram();

        applicationContext.close();
    }
}
