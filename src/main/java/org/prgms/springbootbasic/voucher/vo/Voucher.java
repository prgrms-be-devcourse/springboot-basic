package org.prgms.springbootbasic.voucher.vo;

import java.util.UUID;

public interface Voucher {
	/**
	 * Voucher의 아이디를 조회하는 메서드
	 *
	 * @return voucherId
	 */
	UUID getVoucherId();

	/**
	 * Voucher의 종류(FixAmountVoucher, PercentDiscountVoucher)을 반환하는 메서드
	 * @return VoucherType
	 */
	VoucherType getVoucherType();

	long getValue();

	/**
	 * 기존 가격에서 할인한 후의 가격을 반환하는 메서드
	 *
	 * @param beforeDiscount
	 * @return 할인 후 가격
	 */
	long discount(long beforeDiscount);


}
