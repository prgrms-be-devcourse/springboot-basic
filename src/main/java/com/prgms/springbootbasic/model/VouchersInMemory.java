package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.DuplicationKeyException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class VouchersInMemory {
	
	private static final Map<UUID, Voucher> storage = new HashMap<>();
	
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.of(storage.get(voucherId));
	}
	
	public List<Voucher> findAll() {
		return storage.values()
				.stream()
				.toList();
	}
	
	public void save(Voucher voucher) {
		if (storage.containsKey(voucher.getVoucherId())) throw new DuplicationKeyException("이미 존재하는 키 입니다. voucherId : " + voucher.getVoucherId());
		storage.put(voucher.getVoucherId(), voucher);
	}
	
}
