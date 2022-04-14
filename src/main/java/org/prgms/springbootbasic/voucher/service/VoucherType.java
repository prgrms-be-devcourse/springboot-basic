package org.prgms.springbootbasic.voucher.service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

import org.prgms.springbootbasic.voucher.vo.FixedAmountVoucher;
import org.prgms.springbootbasic.voucher.vo.PercentDiscountVoucher;
import org.prgms.springbootbasic.voucher.vo.Voucher;

public enum VoucherType {
	FIXAMOUNTVOUCHER("1") {
		@Override
		public Voucher createVoucher(long param) {
			return new FixedAmountVoucher(param);
		}
	},
	PERCENTDISCOUNTVOUCHER("2") {
		@Override
		public Voucher createVoucher(long param) {
			return new PercentDiscountVoucher((int)param);
		}
	};

	private final String button;

	VoucherType(String button) {
		this.button = button;
	}

	/**
	 * button을 입력받고 그에 맞는 VoucherType을 반환하는 메서드
	 *
	 * @param button
	 * @return button에 맞는 VoucherType
	 * @throws IllegalArgumentException
	 */
	public static VoucherType getVoucherType(String button) {
		return Arrays.stream(VoucherType.values())
			.filter(type -> type.button.equals(button))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException(
				MessageFormat.format("button은 1, 2 둘중 하나를 입력해야 합니다. button : {0}", button)));
	}

	/**
	 * VoucherType에 해당하는 Voucher를 생성하는 메서드
	 *
	 * @param param
	 * @return Voucher
	 */
	public abstract Voucher createVoucher(long param);

}
