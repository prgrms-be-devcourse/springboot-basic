package com.example.voucher.domain;

import java.util.UUID;

public interface Voucher {
	UUID getVoucherId();

	long discount(long beforeAmount);

}
