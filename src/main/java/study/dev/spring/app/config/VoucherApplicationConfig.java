package study.dev.spring.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import study.dev.spring.app.CommandExecutor;
import study.dev.spring.app.ConsoleVoucherApplicationRunner;
import study.dev.spring.app.exception.ConsoleVoucherExceptionHandler;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;

@Configuration
public class VoucherApplicationConfig {

	@Bean
	public ConsoleVoucherApplicationRunner voucherApplicationRunner(
		CommandExecutor commandExecutor,
		InputHandler inputHandler,
		OutputHandler outputHandler
	) {
		ConsoleVoucherApplicationRunner target = new ConsoleVoucherApplicationRunner(
			commandExecutor, inputHandler, outputHandler
		);

		return new ConsoleVoucherExceptionHandler(target);
	}
}
