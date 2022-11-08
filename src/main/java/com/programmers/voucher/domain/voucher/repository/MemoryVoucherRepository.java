package com.programmers.voucher.domain.voucher.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.exception.ExceptionMessage;
import com.programmers.voucher.exception.VoucherNotFoundException;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

	private static final HashMap<UUID, Voucher> repository = new HashMap<>();
	private static final Logger log = LoggerFactory.getLogger(MemoryVoucherRepository.class);

	@Override
	public void save(Voucher voucher) {
		repository.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public Voucher findByUUID(UUID voucherId) {
		return Optional.ofNullable(repository.get(voucherId))
			.orElseThrow(() -> {
				log.error(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
				throw new VoucherNotFoundException();
			});
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(repository.values());
	}

	@Override
	public void clear() {
		repository.clear();
	}
}
