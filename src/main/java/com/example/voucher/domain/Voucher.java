package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.constant.VoucherType;

public interface Voucher {

	UUID getVoucherId();

	Long getValue();

	VoucherType getVoucherType();

	long discount(long beforeAmount);

}
