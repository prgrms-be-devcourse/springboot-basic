package study.dev.spring.app;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.Arrays;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.exception.GlobalException;
import study.dev.spring.voucher.presentation.VoucherController;

@RequiredArgsConstructor
public enum VoucherMethodExecutor {

	CREATE(VoucherController::createVoucher),
	LIST(VoucherController::findAllVouchers);

	private final Consumer<VoucherController> target;

	public void execute(final VoucherController controller) {
		target.accept(controller);
	}

	public static VoucherMethodExecutor convert(final String name) {
		return Arrays.stream(values())
			.filter(matcher -> matcher.name().equalsIgnoreCase(name))
			.findFirst()
			.orElseThrow(() -> new GlobalException(UNSUPPORTED_FUNCTION_NAME));
	}
}
