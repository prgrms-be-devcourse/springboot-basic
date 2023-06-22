package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.DuplicationKeyException;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

public class VouchersInMemory {
	
	private static final Map<UUID, Voucher> storage = new HashMap<>();
	
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.of(storage.get(voucherId));
	}
	
	public List<Voucher> findAll() {
		return storage.keySet()
				.stream()
				.map(key -> storage.get(key))
				.collect(Collectors.toList());
	}
	
	public void save(Voucher voucher) {
		if (storage.containsKey(voucher.getVoucherId())) throw new DuplicationKeyException("이미 존재하는 키 입니다. voucherId : " + voucher.getVoucherId());
		storage.put(voucher.getVoucherId(), voucher);
	}
	
}
