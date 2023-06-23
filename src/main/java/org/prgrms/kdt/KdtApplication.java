package org.prgrms.kdt;

import org.prgrms.kdt.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);
		//MainController mainController = ac.getBean(MainController.class);
		//mainController.startControl();
	}

}
