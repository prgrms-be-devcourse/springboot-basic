package com.example.springbootbasic;

import com.example.springbootbasic.voucher.MemoryVoucherRepository;
import com.example.springbootbasic.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication {
	private final static VoucherRepository voucherRepository = new MemoryVoucherRepository();

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBasicApplication.class, args);
		System.out.println("Hello Springboot");

		Console console = new Console();
		VoucherManager voucherManager = new CliVoucherManager(console, console, voucherRepository);
		voucherManager.run();
	}

}
