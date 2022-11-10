package com.programmers.voucher.domain.voucher.model;

import java.util.UUID;

public interface Voucher {

	UUID getVoucherId();

	double discount(double beforeDiscount);
}
