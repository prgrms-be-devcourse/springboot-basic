package com.example.springbootbasic;

import com.example.springbootbasic.config.AppConfiguration;
import com.example.springbootbasic.console.ConsoleStatus;
import com.example.springbootbasic.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import static com.example.springbootbasic.console.ConsoleStatus.END;
import static com.example.springbootbasic.console.ConsoleStatus.SUCCESS;

@SpringBootApplication
@EnableConfigurationProperties(AppConfiguration.class)
public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
		MainController mainController = ac.getBean(MainController.class);

		ConsoleStatus status = SUCCESS;
		while (status != END) {
			status = mainController.executeVoucherProgram().getStatus();
		}
	}
}
