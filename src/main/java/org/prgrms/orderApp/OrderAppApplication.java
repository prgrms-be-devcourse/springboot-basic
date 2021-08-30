package org.prgrms.orderApp;

import org.prgrms.orderApp.presentation.commandOperator.CommandOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderAppApplication {
	private final static Logger logger = LoggerFactory.getLogger(OrderAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(OrderAppApplication.class, args);
		
	}

}
