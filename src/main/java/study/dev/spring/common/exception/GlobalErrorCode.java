package study.dev.spring.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode {

	FILE_READ_EX("파일 read 중 예외가 발생했습니다."),
	FILE_WRITE_EX("파일 write 중 예외가 발생했습니다."),
	UNSUPPORTED_EXT("지원하지 않는 파일 형식입니다"),

	ONLY_NUMBER("숫자만 입력해주세요");

	private final String message;
}
