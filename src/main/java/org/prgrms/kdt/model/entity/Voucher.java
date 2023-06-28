package org.prgrms.kdt.model.entity;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public interface Voucher {

	Long getVoucherId();

	Amount getAmount();

	VoucherType getVoucherType();

	long discount(long beforeDiscount);
}
