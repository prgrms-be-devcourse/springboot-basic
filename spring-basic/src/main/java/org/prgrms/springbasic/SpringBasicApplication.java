package org.prgrms.springbasic;

import org.prgrms.springbasic.domain.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBasicApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(SpringBasicApplication.class, args);
		var app = context.getBean(Application.class);
		app.run();
	}
}
