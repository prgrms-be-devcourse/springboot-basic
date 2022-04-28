package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public interface Voucher {
	UUID getVoucherId();

	long getDiscountInfo();

	VoucherType getType();

	LocalDateTime getCreatedTime();

	long discount(long beforeDiscount);
}
