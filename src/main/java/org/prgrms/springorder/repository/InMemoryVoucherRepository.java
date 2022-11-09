package org.prgrms.springorder.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgrms.springorder.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("local")
@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

	private final Map<UUID, Voucher> map = new HashMap<>();

	@Override
	public void save(Voucher voucher) {
		map.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(map.values());
	}
}
