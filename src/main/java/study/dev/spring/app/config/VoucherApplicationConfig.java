package study.dev.spring.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import study.dev.spring.app.ConsoleVoucherApplicationRunner;
import study.dev.spring.app.exception.ConsoleVoucherExceptionHandler;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.customer.presentation.CustomerController;
import study.dev.spring.voucher.presentation.ConsoleVoucherController;

@Configuration
public class VoucherApplicationConfig {

	@Bean
	public ConsoleVoucherApplicationRunner voucherApplicationRunner(
		final ConsoleVoucherController consoleVoucherController,
		final CustomerController customerController,
		final InputHandler inputHandler,
		final OutputHandler outputHandler
	) {
		ConsoleVoucherApplicationRunner target = new ConsoleVoucherApplicationRunner(
			consoleVoucherController, customerController, inputHandler, outputHandler
		);

		return new ConsoleVoucherExceptionHandler(target);
	}
}
