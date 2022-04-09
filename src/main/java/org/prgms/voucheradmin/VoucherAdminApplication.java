package org.prgms.voucheradmin;

import org.prgms.voucheradmin.domain.console.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherAdminApplication {
	public static void main(String[] args) {
		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

		ApplicationContext applicationContext = SpringApplication.run(VoucherAdminApplication.class, args);
		Console console = applicationContext.getBean(Console.class);
		console.run();
	}
}
