package me.programmers.springboot.basic.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBasicApplication {

	public static void main(String[] args) {
		ApplicationController controller = new ApplicationController(new SpringApplication(SpringBootBasicApplication.class), args);
		controller.runCommandLineApp();
	}
}
