package com.programmers.order.manager;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

public interface VoucherInternalManager {
	Voucher create(UUID voucherId, long discountValue, long quantity,
			LocalDateTime expirationAt, LocalDateTime createdAt, LocalDateTime updatedAt);

	VoucherType getType();
}
