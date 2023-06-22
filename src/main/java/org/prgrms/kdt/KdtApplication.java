package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value="application.yaml")
public class KdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);
	}

}
