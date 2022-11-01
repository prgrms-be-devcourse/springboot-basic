package com.programmers.voucher.domain;

import java.util.UUID;

public interface Voucher {

	UUID getVoucherId();

	int discount(int beforeDiscount);
}
