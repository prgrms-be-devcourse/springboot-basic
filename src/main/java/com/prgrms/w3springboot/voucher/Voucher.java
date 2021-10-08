package com.prgrms.w3springboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
	UUID getVoucherId();

	long getAmount();

	VoucherType getVoucherType();

	LocalDateTime getCreatedAt();

	long discount(long beforeDiscount);

	void validate(long value);
}
