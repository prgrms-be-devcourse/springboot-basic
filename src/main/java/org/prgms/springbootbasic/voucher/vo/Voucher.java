package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class Voucher {
	private final UUID voucherId;
	private final VoucherType type;
	private final int value;

	public Voucher(UUID voucherId, VoucherType type, int value) {
		this.voucherId = voucherId;
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Voucher{" +
			"voucherId=" + voucherId +
			", type=" + type +
			", value=" + value +
			'}';
	}

	/**
	 * 기존 가격에서 할인율을 곱한 가격을 제외한 나머지 가격을 반환한다.
	 *
	 * @param beforeDiscount
	 * @return discount 후의 값
	 */
	abstract long discount(long beforeDiscount);
}
