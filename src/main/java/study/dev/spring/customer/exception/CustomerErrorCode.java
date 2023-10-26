package study.dev.spring.customer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCode {

	NOT_EXIST("존재하지 않는 고객입니다.")
	;

	private final String message;
}
