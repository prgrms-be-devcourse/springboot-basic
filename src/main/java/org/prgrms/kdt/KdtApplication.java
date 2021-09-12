package org.prgrms.kdt;

import org.prgrms.kdt.assignments.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(KdtApplication.class, args);
		CommandLineApplication commandLineApplication = context.getBean(CommandLineApplication.class);
		commandLineApplication.run();
	}

}
