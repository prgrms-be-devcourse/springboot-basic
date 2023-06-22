package org.prgrms.kdt.enums;

import java.util.Arrays;
import java.util.Optional;

import javax.lang.model.element.UnknownElementException;

import org.springframework.beans.factory.annotation.Value;

public enum Voucher {
	FixedAmountVoucher(1),
	PercentDiscountVoucher(2);

	private int voucherIdx;

	private final String unSupportedVoucherMessage= "잘 못된 입력 입니다.";
	Voucher(int voucherIdx) {
		this.voucherIdx = voucherIdx;
	}

	public Voucher valueOf(int voucherIdx) {
		return Arrays.stream(Voucher.values())
			.filter(voucher -> voucher.getVoucherIdx() == voucherIdx)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(unSupportedVoucherMessage));
	}

	public int getVoucherIdx() {
		return voucherIdx;
	}
}
