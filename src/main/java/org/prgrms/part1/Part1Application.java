package org.prgrms.part1;

import org.prgrms.part1.engine.VoucherManager;
import org.prgrms.part1.engine.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Part1Application {
    private static final Logger logger = LoggerFactory.getLogger(Part1Application.class);
    public static void main(String[] args) {
        var springApplication = new SpringApplication(Part1Application.class);
        //springApplication.setAdditionalProfiles("dev");
        var applicationContext = springApplication.run(args);

        var voucherProperties = applicationContext.getBean(VoucherProperties.class);
        logger.info("environment => {}", voucherProperties.getEnvironment());
        var voucherService = applicationContext.getBean(VoucherService.class);
        var console = new Console();

        new VoucherManager(voucherService, console, console, logger).run();

    }
}
