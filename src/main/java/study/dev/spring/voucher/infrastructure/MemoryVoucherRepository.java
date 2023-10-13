package study.dev.spring.voucher.infrastructure;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;

public class MemoryVoucherRepository implements VoucherRepository {

	private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

	@Override
	public void save(final Voucher voucher) {
		storage.put(voucher.getUuid(), voucher);
	}

	@Override
	public Voucher findById(final UUID uuid) {
		return storage.get(uuid);
	}
}
