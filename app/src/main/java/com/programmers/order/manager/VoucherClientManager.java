package com.programmers.order.manager;

import java.time.LocalDateTime;

import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

public interface VoucherClientManager {

	Voucher create(long quantity, long discountValue, LocalDateTime expirationAt);

	VoucherType getType();

}
