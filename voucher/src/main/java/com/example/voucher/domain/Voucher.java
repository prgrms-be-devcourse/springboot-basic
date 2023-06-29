package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;

public interface Voucher {

	UUID getVoucherId();

	Long getValue();

	VoucherType getVoucherType();

	long discount(long beforeAmount);

}
