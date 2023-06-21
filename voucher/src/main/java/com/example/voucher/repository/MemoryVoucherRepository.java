package com.example.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.example.voucher.domain.Voucher;

public class MemoryVoucherRepository implements VoucherRepository {
	private final Map<UUID, Voucher> map = new ConcurrentHashMap<>();

	@Override
	public UUID save(Voucher voucher) {
		UUID voucherId = voucher.getVoucherId();
		map.put(voucherId, voucher);
		return map.get(voucherId).getVoucherId();
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(map.values());
	}
}
