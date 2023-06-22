package com.prgms.springbootbasic;

import com.prgms.springbootbasic.controller.VoucherController;
import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.ui.InputView;
import com.prgms.springbootbasic.ui.OutputView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	
	@Bean
	public VoucherController voucherController() { return new VoucherController(console()); }
	
	@Bean
	public Console console() { return new Console(inputView(), outputView()); }
	
	@Bean
	public InputView inputView() { return new InputView(); }
	
	@Bean
	public OutputView outputView() { return new OutputView(); }
	
}
