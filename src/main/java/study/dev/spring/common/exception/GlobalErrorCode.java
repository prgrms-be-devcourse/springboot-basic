package study.dev.spring.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode {

	FILE_READ_EX("파일 read 중 예외가 발생했습니다. 파일 이름을 확인해주세요");

	private final String message;
}
