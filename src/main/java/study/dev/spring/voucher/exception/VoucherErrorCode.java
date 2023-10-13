package study.dev.spring.voucher.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoucherErrorCode {

	;

	private final String message;
}
