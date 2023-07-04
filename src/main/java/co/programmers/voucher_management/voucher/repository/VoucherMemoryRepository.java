package co.programmers.voucher_management.voucher.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.voucher.entity.Voucher;

@Repository
@Profile({"local", "test"})
public class VoucherMemoryRepository implements VoucherRepository {
	private final Map<Integer, Voucher> repository = new ConcurrentHashMap<>();
	private static final int VOUCHER_ID_RANDOMNESS = 1000;
	private static final Random random = new Random();

	@Override
	public Voucher create(Voucher voucher) {
		repository.put(assignId(), voucher);
		return voucher;
	}

	public List<Voucher> findAll() {
		return repository.values()
				.stream()
				.filter(v -> v.getStatus() == 'Y')
				.collect(Collectors.toList());
	}

	@Override
	public void deleteOf(int id) {
		repository.get(id)
				.delete();
	}

	private int assignId() {
		random.setSeed(System.currentTimeMillis());
		return Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
				+ random.nextInt(VOUCHER_ID_RANDOMNESS));
	}

}
