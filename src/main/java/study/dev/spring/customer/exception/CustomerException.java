package study.dev.spring.customer.exception;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.exception.CommonException;

@RequiredArgsConstructor
public class CustomerException extends CommonException {

	private final CustomerErrorCode errorCode;

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}
}
