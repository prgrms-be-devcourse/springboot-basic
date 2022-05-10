package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

	UUID getVoucherId();

	VoucherType getVoucherType();

	long getDiscountValue();

	long getQuantity();

	LocalDateTime getExpirationAt();

	LocalDateTime getCreatedAt();

	LocalDateTime getUpdatedAt();

	long discount(long beforeDiscount);
}
