package com.programmers.voucher.repository.voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.voucher.Voucher;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

	private static final HashMap<UUID, Voucher> repository = new HashMap<>();

	@Override
	public void save(Voucher voucher) {
		repository.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public Voucher findByUUID(UUID voucherId) {
		return repository.get(voucherId);
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
