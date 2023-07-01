package com.prgms.springbootbasic.voucher.model;

import com.prgms.springbootbasic.global.util.ExceptionMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("test")
public class VouchersInMemory implements VouchersStorage {
	
	private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
	
	public List<Voucher> findAll() {
		return storage.values()
				.stream()
				.toList();
	}
	
	public void save(Voucher voucher) {
		throwDuplicationKeyException(voucher.getVoucherId());
		storage.put(voucher.getVoucherId(), voucher);
	}

	private void throwDuplicationKeyException(UUID voucherId) {
		if (storage.containsKey(voucherId)) {
			throw new IllegalArgumentException(ExceptionMessage.DUPLICATION_KEY.getMessage());
		}
	}
	
}
