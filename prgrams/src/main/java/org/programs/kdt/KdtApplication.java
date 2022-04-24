package org.programs.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(KdtApplication.class, args);
		applicationContext.getBean(App.class).run();
	}

}
