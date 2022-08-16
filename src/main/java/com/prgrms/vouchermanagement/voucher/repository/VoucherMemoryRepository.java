package com.prgrms.vouchermanagement.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.commons.exception.UpdateFailException;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {
	private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
	private final Logger logger = LoggerFactory.getLogger(VoucherMemoryRepository.class);

	@Override
	public List<Voucher> findAll(long offset, int limit) {
		// TODO
		return null;
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(storage.values());
	}

	@Override
	public Voucher insert(Voucher voucher) {
		if (storage.containsKey(voucher.getVoucherId())) {
			logger.info("Voucher {} insert fail", voucher);

			throw new UpdateFailException();
		}
		storage.put(voucher.getVoucherId(), voucher);
		logger.info("Save new Voucher : {}", voucher);

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		return Optional.ofNullable(storage.get(id));
	}

	@Override
	public int deleteById(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("id 는 null 이 아니어야 합니다");
		}

		if (storage.remove(id) == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int deleteAll() {
		int deletedCount = storage.size();

		storage.clear();

		return deletedCount;
	}
}
