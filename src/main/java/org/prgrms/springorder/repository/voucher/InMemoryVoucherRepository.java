package org.prgrms.springorder.repository.voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class InMemoryVoucherRepository implements VoucherRepository {

	private final Map<UUID, Voucher> memory = new HashMap<>();

	@Override
	public void save(Voucher voucher) {
		memory.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public Optional<Voucher> findById(UUID uuid) {
		return Optional.ofNullable(memory.get(uuid));
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(memory.values());
	}
}
