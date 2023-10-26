package study.dev.spring.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.dev.spring.app.ConsoleVoucherApplicationRunner;
import study.dev.spring.common.exception.CommonException;

public class ConsoleVoucherExceptionHandler extends ConsoleVoucherApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(ConsoleVoucherExceptionHandler.class);

	private final ConsoleVoucherApplicationRunner target;

	public ConsoleVoucherExceptionHandler(
		final ConsoleVoucherApplicationRunner target
	) {
		super(null, null, null);
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
