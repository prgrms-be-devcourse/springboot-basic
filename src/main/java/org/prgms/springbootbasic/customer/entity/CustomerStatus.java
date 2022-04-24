package org.prgms.springbootbasic.customer.entity;

import java.util.Arrays;

public enum CustomerStatus {
	AVAILABLE, BLOCKED;

	/**
	 * status를 입력받으면 CustomerStatus로 변환시켜주는 메서
	 * @param status
	 * @return드 CustomerStatus
	 */
	public static CustomerStatus of(String status) {
		return Arrays.stream(CustomerStatus.values())
			.filter(cs -> cs.name().equals(status))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException("잘못된 customer_status를 입력하셨습니다."));

	}
}
