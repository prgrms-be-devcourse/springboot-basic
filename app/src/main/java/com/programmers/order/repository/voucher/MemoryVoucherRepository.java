package com.programmers.order.repository.voucher;

import java.security.DomainLoadStoreParameter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.text.html.Option;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.DomainException;
import com.programmers.order.message.ErrorMessage;

@Profile("memory")
@Component
public class MemoryVoucherRepository implements VoucherRepository {

	private static final ConcurrentHashMap<UUID, Voucher> memory = new ConcurrentHashMap<>();

	@Override
	public Voucher insert(Voucher voucher) {
		memory.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		return memory.values().stream()
				.sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
				.toList();
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.ofNullable(memory.get(voucherId));
	}

	@Override
	public void delete(UUID voucherId) {
		if (memory.contains(voucherId)) {
			memory.remove(voucherId);
		}

		throw new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR);
	}

	@Override
	public int exsitsByVocuher(UUID voucherId) {
		return memory.contains(voucherId) ? 1 : -1;
	}
}
