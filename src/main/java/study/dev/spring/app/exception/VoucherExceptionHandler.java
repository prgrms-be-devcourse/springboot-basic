package study.dev.spring.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.dev.spring.app.VoucherApplicationRunner;
import study.dev.spring.common.exception.CommonException;

public class VoucherExceptionHandler extends VoucherApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(VoucherExceptionHandler.class);

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
			logger.error("custom exception : ", e);
			System.out.println(e.getMessage());
		} catch (ExitException e) {
			System.exit(0);
		} catch (Exception e) {
			logger.error("system exception : ", e);
			System.out.println("애플리케이션 내부 오류입니다. 다시 시도해주세요");
		}
	}
}
