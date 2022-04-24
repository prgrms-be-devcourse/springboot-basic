package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {
	private static final Map<Long, Voucher> store = new ConcurrentHashMap<>();

	@Override
	public Voucher save(Voucher voucher) {
		voucher.setVoucherId(VoucherIdGenerator.generateVoucherId());
		store.put(voucher.getVoucherId(), voucher);
		return voucher;
	}
}
