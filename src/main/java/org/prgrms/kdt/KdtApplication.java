package org.prgrms.kdt;

import org.prgrms.kdt.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:./application.properties")
@ComponentScan(
		basePackages = {"org.prgrms.kdt"}
)
public class KdtApplication {

	private static final Logger logger = LoggerFactory.getLogger(KdtApplication.class);
	public static void main(String[] args) {
		logger.info("Application Start!");
		var applicationContext = SpringApplication.run(KdtApplication.class, args);
		applicationContext.getBean(MainController.class).run();
		logger.info("Application End!");
	}

}
