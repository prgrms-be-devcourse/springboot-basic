package com.programmers.springbootbasic;

import com.programmers.springbootbasic.menu.controller.MenuController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootBasicApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
		MenuController menuController = applicationContext.getBean(MenuController.class);
		menuController.run();
	}

}
