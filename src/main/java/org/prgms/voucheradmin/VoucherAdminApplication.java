package org.prgms.voucheradmin;

import org.prgms.voucheradmin.domain.voucher.console.VoucherConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class VoucherAdminApplication {
	public static void main(String[] args) {
		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

		ApplicationContext applicationContext = SpringApplication.run(VoucherAdminApplication.class, args);
		VoucherConsole voucherConsole = applicationContext.getBean(VoucherConsole.class);
		voucherConsole.run();
	}
}
