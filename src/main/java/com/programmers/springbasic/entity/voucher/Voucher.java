package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

public interface Voucher {
	UUID getVoucherId();
	VoucherType getVoucherType();
	long getDiscountValue();
}
