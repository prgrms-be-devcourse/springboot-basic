package org.prgrms.kdt;

import org.prgrms.kdt.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		MainController mainController = ac.getBean(MainController.class);
		mainController.startControl();
	}
}
