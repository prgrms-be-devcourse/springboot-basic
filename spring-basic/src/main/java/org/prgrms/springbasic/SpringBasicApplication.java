package org.prgrms.springbasic;

import org.prgrms.springbasic.config.AppConfig;
import org.prgrms.springbasic.domain.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBasicApplication.class, args);
	}
}
