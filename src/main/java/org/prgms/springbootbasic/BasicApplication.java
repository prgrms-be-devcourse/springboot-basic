package org.prgms.springbootbasic;

import org.prgms.springbootbasic.common.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BasicApplication.class, args);
		Console console = applicationContext.getBean(Console.class);
		console.run();
	}

}
