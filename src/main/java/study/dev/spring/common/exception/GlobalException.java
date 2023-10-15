package study.dev.spring.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GlobalException extends CommonException {

	private final GlobalErrorCode errorCode;

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}
}
