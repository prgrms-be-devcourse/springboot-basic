package com.programmers.voucher.domain.voucher.repository;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.core.exception.NotFoundException;
import com.programmers.voucher.domain.voucher.model.Voucher;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

	private static final Map<UUID, Voucher> repository = new HashMap<>();
	private static final Logger log = LoggerFactory.getLogger(MemoryVoucherRepository.class);

	@Override
	public Voucher save(Voucher voucher) {
		return repository.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public Voucher findById(UUID voucherId) {
		return Optional.ofNullable(repository.get(voucherId))
			.orElseThrow(() -> {
				log.error(VOUCHER_NOT_FOUND.getMessage());
				throw new NotFoundException(VOUCHER_NOT_FOUND.getMessage());
			});
	}

	@Override
	public void deleteById(UUID voucherId) {
		Optional.ofNullable(repository.get(voucherId))
			.ifPresentOrElse(voucher -> repository.remove(voucherId),
				() -> {
					log.error(VOUCHER_NOT_FOUND.getMessage());
					throw new NotFoundException(VOUCHER_NOT_FOUND.getMessage());
				}
			);
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
