package org.prgms.voucheradmin;

import org.prgms.voucheradmin.domain.administrator.controller.Administrator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class VoucherAdminApplication {
	public static void main(String[] args) {
		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

		ApplicationContext applicationContext = SpringApplication.run(VoucherAdminApplication.class, args);
		 Administrator administrator = applicationContext.getBean(Administrator.class);
		 administrator.run();
	}
}
