package com.prgrm.kdt;

import com.prgrm.kdt.customer.domain.CustomerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
//@ComponentScan(
//        basePackages = {"com.prgrm.kdt.order", "com.prgrm.kdt.voucher", "com.prgrm.kdt.customer", "com.prgrm.kdt.config"}
//)
public class KdtApplication {

    private static final Logger logger = LoggerFactory.getLogger(KdtApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("dev");
        ConfigurableApplicationContext applicationContext = springApplication.run(args);

//        CustomerProperties customerProperties = applicationContext.getBean(CustomerProperties.class);
//        Resource resource = applicationContext.getResource("file:src/main/resources/customer_blacklist.csv");
//        List<String> blacklists = Files.readAllLines(resource.getFile().toPath());
//        System.out.println(blacklists.stream().reduce("", (a, b) -> a + "\n" + b));
    }

}
