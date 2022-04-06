package org.prgms.voucheradmin;

import org.prgms.voucheradmin.domain.voucher.console.VoucherConsole;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class VoucherAdminApplication {
	public static void main(String[] args) {
		 ApplicationContext applicationContext = new AnnotationConfigApplicationContext(VoucherAdminApplication.class);
		 VoucherConsole voucherConsole = applicationContext.getBean(VoucherConsole.class);
		 voucherConsole.run();
	}
}
