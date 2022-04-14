package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.util.FilePathProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(FilePathProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
		ac.getBean(VoucherManagement.class).run();
	}

}
