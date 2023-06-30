package com.example.springbootbasic;

import com.example.springbootbasic.voucher.CliVoucherApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication {
	private final CliVoucherApplication cliVoucherApplication;

	public SpringbootBasicApplication(CliVoucherApplication cliVoucherApplication) {
		this.cliVoucherApplication = cliVoucherApplication;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBasicApplication.class, args);
//		cliVoucherApplication.run();
	}

}
