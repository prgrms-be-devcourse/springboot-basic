package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

	int discount(int beforeDiscount);

	int getValue();

	void changeValue(int value);

	LocalDateTime getCreatedAt();

	UUID getVoucherId();

	VoucherType getVoucherType();

}
