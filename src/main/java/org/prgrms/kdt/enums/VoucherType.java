package org.prgrms.kdt.enums;

import java.util.Arrays;

import org.prgrms.kdt.model.domain.Voucher;
import org.prgrms.kdt.util.FixedVoucherFactory;
import org.prgrms.kdt.util.PercentVoucherFactory;
import org.prgrms.kdt.util.VoucherFactory;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VoucherType {
	FixedAmountVoucher(1, new FixedVoucherFactory()),
	PercentDiscountVoucher(2, new PercentVoucherFactory());

	private final static String UN_SUPPORTED_VOUCHER_MESSAGE = "잘 못된 입력 입니다.";
	private final int voucherIdx;
	private final VoucherFactory voucherFactory;

	VoucherType(int voucherIdx, VoucherFactory voucherFactory) {
		this.voucherIdx = voucherIdx;
		this.voucherFactory = voucherFactory;
	}

	public static VoucherType valueOf(int voucherIdx) {
		return Arrays.stream(VoucherType.values())
			.filter(voucher -> voucher.voucherIdx == voucherIdx)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(UN_SUPPORTED_VOUCHER_MESSAGE));
	}

	@JsonCreator
	public static VoucherType value(String voucherString) {
		return VoucherType.valueOf(voucherString);
	}

	public int getVoucherIdx() {
		return voucherIdx;
	}

	public Voucher createVoucher(Long id, int amount, VoucherType voucherType) {
		return voucherFactory.createVoucher(id, amount, voucherType);
	}
}
