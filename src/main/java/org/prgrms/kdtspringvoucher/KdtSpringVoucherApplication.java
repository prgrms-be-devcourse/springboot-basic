package org.prgrms.kdtspringvoucher;

import org.prgrms.kdtspringvoucher.cmdapp.cmdapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringVoucherApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(KdtSpringVoucherApplication.class, args);
//		applicationContext.getBean(cmdapp.class).run();
	}
}
