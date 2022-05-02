package org.prgms.kdtspringvoucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtSpringVoucherApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KdtSpringVoucherApplication.class);
        springApplication.setAdditionalProfiles("dev");
        springApplication.run(args);
    }
}
