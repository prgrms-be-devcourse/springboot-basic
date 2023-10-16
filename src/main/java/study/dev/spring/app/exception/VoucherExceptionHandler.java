package study.dev.spring.app.exception;

import study.dev.spring.app.VoucherApplicationRunner;
import study.dev.spring.common.exception.CommonException;

public class VoucherExceptionHandler extends VoucherApplicationRunner {

	private final VoucherApplicationRunner target;

	public VoucherExceptionHandler(
		final VoucherApplicationRunner target
	) {
		super(null, null, null, null);
		this.target = target;
	}

	@Override
	public void run() {
		try {
			target.run();
		} catch (CommonException e) {
			// todo : 예외 로그 추가
			System.out.println(e.getMessage());
		} catch (ExitException e) {
			System.exit(0);
		}
	}
}
