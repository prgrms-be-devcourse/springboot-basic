package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:./application.properties")
@ServletComponentScan
public class KdtApplication {
	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);
	}

}
