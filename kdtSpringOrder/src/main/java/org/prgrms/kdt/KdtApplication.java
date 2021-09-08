package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {

    private final static Logger logger = LoggerFactory.getLogger(KdtApplication.class);

	public static void main(String[] args) {

        logger.info("voucher Process started.");

        var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("prod");
		ConfigurableApplicationContext applicationContext = springApplication.run(args);
	}
}