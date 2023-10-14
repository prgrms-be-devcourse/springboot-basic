package org.prgrms.vouchermanager;

import org.prgrms.vouchermanager.handler.Handler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class VouchermanagerApplication {

	public static void main(String[] args) throws IOException {

		ConfigurableApplicationContext ac = SpringApplication.run(VouchermanagerApplication.class, args);
		Handler voucherManager = ac.getBean(Handler.class);
		voucherManager.init();
	}

}
