package org.prgms.kdtspringvoucher;

import org.prgms.kdtspringvoucher.commandLine.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringVoucherApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KdtSpringVoucherApplication.class);
        springApplication.setAdditionalProfiles("dev");
        ApplicationContext applicationContext = springApplication.run(args);

        applicationContext.getBean(CommandLineApplication.class).run();
    }
}
