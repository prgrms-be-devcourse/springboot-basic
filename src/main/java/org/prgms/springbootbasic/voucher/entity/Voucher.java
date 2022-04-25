package org.prgms.springbootbasic.voucher.entity;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class Voucher {
	/* Voucher의 식별번호, ID*/
	private final UUID voucherId;
	/* VoucherType { FIXEDAMOUNTVOUCHER, PERCENTDISCOUNTVOUCHER }*/
	private final VoucherType type;
	/* Voucher의 금액 혹은 할인율*/
	private final int value;

	public Voucher(UUID voucherId, VoucherType type, int value) {
		checkArgument(voucherId != null, "VoucherId는 null이면 안됩니다.");
		checkArgument(type != null, "type은 null이면 안됩니다.");
		checkArgument(value > 0, "value는 0보다 커야합니다. 안됩니다.");

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
