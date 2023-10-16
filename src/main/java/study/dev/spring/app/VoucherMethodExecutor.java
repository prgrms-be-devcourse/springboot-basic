package study.dev.spring.app;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.Arrays;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.exception.GlobalException;
import study.dev.spring.voucher.presentation.ConsoleVoucherController;

@RequiredArgsConstructor
public enum VoucherMethodExecutor {

	CREATE(ConsoleVoucherController::createVoucher),
	LIST(ConsoleVoucherController::findAllVouchers);

	private final Consumer<ConsoleVoucherController> target;

	public void execute(final ConsoleVoucherController controller) {
		target.accept(controller);
	}

	public static VoucherMethodExecutor convert(final String name) {
		return Arrays.stream(values())
			.filter(matcher -> matcher.name().equalsIgnoreCase(name))
			.findFirst()
			.orElseThrow(() -> new GlobalException(UNSUPPORTED_FUNCTION_NAME));
	}
}
