package com.programmers.voucher.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.io.ConsoleInput;
import com.programmers.voucher.io.ConsoleOutput;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.service.VoucherService;

@Configuration
public class AppConfig {

	@Bean
	public Input input() {
		return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
	}

	@Bean
	public Output output() {
		return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
	}

	@Bean
	public VoucherRepository voucherRepository() {
		return new MemoryVoucherRepository();
	}

	@Bean
	public VoucherFactory voucherFactory() {
		return new VoucherFactory();
	}

	@Bean
	public VoucherService voucherService(VoucherRepository repository, VoucherFactory factory) {
		return new VoucherService(repository, factory);
	}

	@Bean
	public VoucherController voucherController(Input input, Output output, VoucherService service) {
		return new VoucherController(input, output, service);
	}
}
