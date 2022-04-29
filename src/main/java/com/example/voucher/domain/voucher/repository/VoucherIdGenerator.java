package com.example.voucher.domain.voucher.repository;

import java.util.concurrent.atomic.AtomicLong;

public class VoucherIdGenerator {
	private static final int INCREMENT_SIZE = 0;
	private static final AtomicLong voucherIdIndex = new AtomicLong(1);

	private VoucherIdGenerator() {}

	public static Long generateVoucherId() {
		return voucherIdIndex.getAndAdd(INCREMENT_SIZE);
	}
}