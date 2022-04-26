package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;

public interface VoucherRepository {
	Voucher save(Voucher voucher);
	void clear();
}
