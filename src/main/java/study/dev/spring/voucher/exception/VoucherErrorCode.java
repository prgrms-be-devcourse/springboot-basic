package study.dev.spring.voucher.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoucherErrorCode {

	NEGATIVE_DISCOUNT_AMOUNT("할인가는 양수만 가능합니다"),
	INVALID_RANGE_DISCOUNT_AMOUNT("0~100 사이의 할인률만 가능합니다");

	private final String message;
}
