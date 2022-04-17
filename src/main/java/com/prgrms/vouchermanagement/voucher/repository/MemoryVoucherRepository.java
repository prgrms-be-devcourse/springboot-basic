package com.prgrms.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
	private final ConcurrentHashMap<UUID, Voucher> storage = new ConcurrentHashMap<>();
	private final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

	@Override
	public List<Voucher> findAllVoucher() {
		return storage.values()
			.stream()
			.collect(Collectors.toList());
	}

	@Override
	public Voucher save(Voucher voucher) {
		storage.put(voucher.getVoucherId(), voucher);

		logger.info("Save new Voucher : {}", voucher);

		return voucher;
	}

	@Override
	public Voucher findById(UUID id) {
		return storage.get(id);
	}
}
