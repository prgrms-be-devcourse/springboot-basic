package com.prgms.springbootbasic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommanmdLineApplication {
	
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		MainController controller = ac.getBean(MainController.class);
		controller.run();
	}
	
}
