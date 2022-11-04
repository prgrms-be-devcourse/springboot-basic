package com.programmers.voucher.domain.voucher;

import java.util.UUID;

public interface Voucher {

	UUID getVoucherId();

	int discount(int beforeDiscount);
}
