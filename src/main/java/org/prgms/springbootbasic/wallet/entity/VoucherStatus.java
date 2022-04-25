package org.prgms.springbootbasic.wallet.entity;

import java.util.Arrays;

public enum VoucherStatus {
	AVAILABLE, USED;

	/**
	 * status를 입력받으면 VoucherStatus로 변환시켜주는 메서
	 * @param status
	 * @return드 VoucherStatus
	 */
	public static VoucherStatus of(String status) {
		return Arrays.stream(VoucherStatus.values())
			.filter(vs -> vs.name().equals(status))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException("잘못된 voucher_status를 입력하셨습니다."));
	}
}
