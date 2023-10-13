package study.dev.spring.voucher.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VoucherType {

	FIXED("정액 할인"),
	PERCENT("정률 할인");

	private final String description;
}
