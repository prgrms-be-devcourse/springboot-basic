package org.prgrms.kdtspringdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
public class KdtSpringDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(KdtSpringDemoApplication.class, args);
	}

}
