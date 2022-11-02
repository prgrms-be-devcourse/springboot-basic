package com.programmers.voucher;

import com.programmers.voucher.config.AppConfig;

public class CommandLineApplication {

	public static void main(String[] args) {
		AppConfig appConfig = new AppConfig();
		appConfig.voucherController().run();
	}
}
