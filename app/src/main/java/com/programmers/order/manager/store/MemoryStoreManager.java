package com.programmers.order.manager.store;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.programmers.order.domain.Voucher;

@Component
public class MemoryStoreManager implements StoreManager {

	private static final ConcurrentHashMap<UUID, Voucher> memory = new ConcurrentHashMap<>();

	@Override
	public Voucher saveVoucher(Voucher voucher) {
		Voucher put = memory.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	@Override
	public List<Voucher> getVouchers() {
		return memory.values().stream()
				.sorted((a, b) -> {
					return b.getCreated().compareTo(a.getCreated());
				})
				.collect(Collectors.toList());
	}
}
