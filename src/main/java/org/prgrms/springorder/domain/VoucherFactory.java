package org.prgrms.springorder.domain;

import static org.prgrms.springorder.domain.ErrorMessage.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.exception.NoSuchVoucherException;

public class VoucherFactory {

	public static Voucher createVoucher(VoucherType voucherType, UUID uuid, double value, LocalDateTime localDateTime) {
		switch (voucherType) {
			case FIXED_AMOUNT -> {
				return new FixedAmountVoucher(uuid, value, localDateTime.withNano(0));
			}
			case PERCENT_DISCOUNT -> {
				return new PercentDiscountVoucher(uuid, value,localDateTime.withNano(0));
			}
			default -> {
				throw new NoSuchVoucherException(NO_SUCH_VOUCHER_MESSAGE);
			}
		}
	}

	public static Voucher createVoucher(VoucherType voucherType, double value) {
		return createVoucher(voucherType, UUID.randomUUID(), value, LocalDateTime.now());
	}

}
