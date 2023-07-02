package co.programmers.voucher_management.voucher.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.voucher.entity.Voucher;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {
	private final Map<String, Voucher> repository = new ConcurrentHashMap<>();
	private final int VOUCHER_ID_RANDOMNESS = 1000;

	private VoucherMemoryRepository() {
	}

	@Override
	public void save(Voucher voucher) {
		repository.put(assignId(), voucher);
	}

	public List<Voucher> findAll() {
		List<Voucher> vouchers = new ArrayList<>();
		for (Map.Entry<String, Voucher> voucher : repository.entrySet()) {
			vouchers.add(voucher.getValue());
		}
		return vouchers;
	}

	@Override
	public int getVoucherCount() {
		return repository.size();
	}

	private String assignId() {
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYMMdd"))
				+ random.nextInt(VOUCHER_ID_RANDOMNESS);
		return id;
	}

}
