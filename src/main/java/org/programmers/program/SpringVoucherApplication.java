package org.programmers.program;

import org.programmers.program.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// @ComponentScan(basePackages = {"org.programmers.program.config"})
public class SpringVoucherApplication {
	public static void main(String[] args){
		SpringApplication.run(SpringVoucherApplication.class, args);
	}

}
