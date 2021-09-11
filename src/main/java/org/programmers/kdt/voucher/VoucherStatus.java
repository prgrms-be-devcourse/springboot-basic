package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherStatus {
	VALID("valid"),     // 사용 가능
	INVALID("invalid"), // 사용 기한 초과
	USED("used"),       // 이미 사용됨
	HALT("halt");       // 쿠폰 효력 정지

	private final String status;

	VoucherStatus(String status) {
		this.status = status;
	}

	public static VoucherStatus of(String status) {
		return Arrays.stream(values())
				.filter(iter -> iter.getStatus().equals(status.toLowerCase()))
				.findAny()
				.orElseThrow(
						() -> new RuntimeException(MessageFormat.format("Invalid Voucher Status : {0}", status))
				);
	}

	public String getStatus() {
		return status;
	}
}
