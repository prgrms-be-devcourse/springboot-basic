package org.prgrms.springorder.domain;

import static org.prgrms.springorder.domain.ErrorMessage.*;

import java.util.UUID;

import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.exception.NoSuchVoucherException;

public class VoucherFactory {

	public static Voucher loadVoucher(VoucherType voucherType, UUID uuid, double value) {
		switch (voucherType) {
			case FIXED_AMOUNT -> {
				return new FixedAmountVoucher(uuid, value);
			}
			case PERCENT_DISCOUNT -> {
				return new PercentDiscountVoucher(uuid, value);
			}
			default -> {
				throw new NoSuchVoucherException(NO_SUCH_VOUCHER_MESSAGE.toString());
			}
		}
	}

	public static Voucher createVoucher(VoucherType voucherType, double value) {
		return loadVoucher(voucherType, UUID.randomUUID(), value);
	}

}
