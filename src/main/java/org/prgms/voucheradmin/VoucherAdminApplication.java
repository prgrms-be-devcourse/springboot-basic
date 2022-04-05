package org.prgms.voucheradmin;

import org.prgms.voucheradmin.domain.voucher.console.VoucherConsole;
import org.prgms.voucheradmin.global.config.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class VoucherAdminApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
		VoucherConsole voucherConsole = applicationContext.getBean(VoucherConsole.class);

		voucherConsole.run();
	}
}
