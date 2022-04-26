package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherMemoryRepository implements VoucherRepository{
	@Override
	public Voucher save(Voucher voucher) {
		return null;
	}
}
