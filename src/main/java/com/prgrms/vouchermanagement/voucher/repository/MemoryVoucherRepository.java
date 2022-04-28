package com.prgrms.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.commons.exception.NotExistException;
import com.prgrms.vouchermanagement.commons.exception.UpdateFailException;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
	private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
	private final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

	@Override
	public List<Voucher> findAllVoucher() {
		return storage.values()
			.stream()
			.collect(Collectors.toList());
	}

	@Override
	public Voucher insert(Voucher voucher) {
		if(storage.containsKey(voucher.getVoucherId())){
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
	public boolean deleteById(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("id 는 null 이 아니어야 합니다");
		}

		Optional.ofNullable(storage.remove(id))
			.orElseThrow(() -> new NotExistException());

		return true;
	}

	@Override
	public boolean deleteAll() {
		storage.clear();

		return true;
	}
}
