package com.voucher.vouchermanagement.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

	private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

	@Override
	public void insert(Voucher voucher) {
		this.store.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(this.store.values());
	}

	@Override
	public List<Voucher> findByType(VoucherType type) {
		return null;
	}

	@Override
	public List<Voucher> findByDate(LocalDateTime startAt, LocalDateTime endAt) {
		return null;
	}

	@Override
	public List<Voucher> findByTypeAndDate(VoucherType type, LocalDateTime startAt, LocalDateTime endAt) {
		return null;
	}

	@Override
	public void deleteById(UUID id) {
		this.store.remove(id);
	}

	@Override
	public void deleteAll() {
		this.store.clear();
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		return Optional.of(this.store.get(id));
	}

	@Override
	public Voucher update(Voucher voucher) {
		return null;
	}
}
