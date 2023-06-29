package com.example.springbootbasic;

import com.example.springbootbasic.voucher.CliVoucherApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication {


	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
		var cliVoucherApplication = applicationContext.getBean(CliVoucherApplication.class);

		cliVoucherApplication.run();
	}

}
