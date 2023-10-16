package study.dev.spring.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import study.dev.spring.app.VoucherApplicationRunner;
import study.dev.spring.app.exception.VoucherExceptionHandler;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.voucher.presentation.VoucherController;

@Configuration
public class VoucherApplicationConfig {

	@Bean
	public VoucherApplicationRunner voucherApplicationRunner(
		final VoucherController voucherController,
		final InputHandler inputHandler,
		final OutputHandler outputHandler
	) {
		VoucherApplicationRunner target = new VoucherApplicationRunner(
			voucherController, inputHandler, outputHandler
		);

		return new VoucherExceptionHandler(target);
	}
}
