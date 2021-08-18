package org.programmers.kdt;

import org.programmers.kdt.io.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		// SpringApplication.run(KdtApplication.class, args);
		Console console = new Console();

		new CommandLineApplication(console, console).run();
	}

}
