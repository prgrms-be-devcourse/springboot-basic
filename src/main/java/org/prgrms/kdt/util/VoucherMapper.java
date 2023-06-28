package org.prgrms.kdt.util;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.FixedDiscountVoucher;
import org.prgrms.kdt.model.entity.PercentDiscountVoucher;
import org.prgrms.kdt.model.entity.Voucher;

public final class VoucherMapper {

	private VoucherMapper() {
	}

	public static VoucherDTO toDTO(Voucher voucher) {
		Long voucherId = voucher.getVoucherId();
		Amount amount = voucher.getAmount();
		VoucherType voucherType = voucher.getVoucherType();

		return new VoucherDTO(voucherId, amount, voucherType);
	}

	public static Voucher toVoucher(VoucherDTO voucherDTO) {
		Long voucherId = voucherDTO.getVoucherId();
		Amount amount = voucherDTO.getAmount();
		VoucherType voucherType = voucherDTO.getVoucherType();

		switch (voucherType) {
			case FixedAmountVoucher -> {
				return new FixedDiscountVoucher(voucherId, amount);
			}

			case PercentDiscountVoucher -> {
				return new PercentDiscountVoucher(voucherId, amount);
			}

			default -> {
				throw new IllegalArgumentException("지원 하지 않는 voucher type이 입력 되었습니다.");
			}
		}
	}
}
