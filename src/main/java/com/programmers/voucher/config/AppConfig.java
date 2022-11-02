package com.programmers.voucher.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.io.ConsoleInput;
import com.programmers.voucher.io.ConsoleOutput;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.service.VoucherService;

public class AppConfig {

	public Input input() {
		return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
	}

	public Output output() {
		return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
	}

	public VoucherRepository voucherRepository() {
		return new MemoryVoucherRepository();
	}

	public VoucherFactory voucherFactory() {
		return new VoucherFactory();
	}

	public VoucherService voucherService() {
		return new VoucherService(voucherRepository(), voucherFactory());
	}

	public VoucherController voucherController() {
		return new VoucherController(input(), output(), voucherService());
	}
}
