package com.example.voucher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.voucher.CommandLineApplication;
import com.example.voucher.repository.MemoryVoucherRepository;
import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.service.VoucherService;

@Configuration
public class AppConfiguration {

	@Bean
	VoucherRepository voucherRepository() {
		return new MemoryVoucherRepository();
	}

	@Bean
	VoucherService voucherService() {
		return new VoucherService(voucherRepository());
	}

	@Bean
	CommandLineApplication commandLineApplication() {
		return new CommandLineApplication(voucherService());
	}

}
