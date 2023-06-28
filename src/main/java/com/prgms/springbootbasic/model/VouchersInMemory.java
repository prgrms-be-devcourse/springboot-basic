package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.DuplicationKeyException;
import com.prgms.springbootbasic.util.ExceptionMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;

@Repository
@Profile("test")
public class VouchersInMemory implements VouchersStorage {
	
	private static final Map<UUID, Voucher> storage = new HashMap<>();
	
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
			throw new DuplicationKeyException(ExceptionMessage.DUPLICATION_KEY);
		}
	}
	
}
