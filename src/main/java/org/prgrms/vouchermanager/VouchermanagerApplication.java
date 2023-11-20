package org.prgrms.vouchermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VouchermanagerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(VouchermanagerApplication.class, args);


//		ConfigurableApplicationContext ac = SpringApplication.run(VouchermanagerApplication.class, args);
//		Handler voucherManager = ac.getBean(Handler.class);
//		voucherManager.init();
	}

}
