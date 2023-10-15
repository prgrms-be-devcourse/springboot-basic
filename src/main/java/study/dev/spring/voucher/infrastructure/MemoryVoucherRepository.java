package study.dev.spring.voucher.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

	private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

	@Override
	public void save(final Voucher voucher) {
		storage.put(voucher.getUuid(), voucher);
	}

	@Override
	public Optional<Voucher> findById(final UUID uuid) {
		return Optional.ofNullable(storage.get(uuid));
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(storage.values());
	}
}
