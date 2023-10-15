package study.dev.spring.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode {

	FILE_READ_EX("파일 read 중 예외가 발생했습니다."),
	FILE_WRITE_EX("파일 write 중 예외가 발생했습니다.");

	private final String message;
}
