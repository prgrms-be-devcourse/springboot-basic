package co.programmers.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import co.programmers.voucher.entity.Voucher;

@Repository
@Component
public class MemoryVoucherRepository implements VoucherRepository {
	private static final MemoryVoucherRepository INSTANCE = new MemoryVoucherRepository();
	private static final ArrayList<Voucher> memoryRepository = new ArrayList<>();

	private MemoryVoucherRepository() {
	}

	public static MemoryVoucherRepository getInstance() {
		return INSTANCE;
	}

	@Override
	public void save(Voucher voucher) {
		memoryRepository.add(voucher);
	}

	public List<Map<String, Object>> inquire() {
		List<Map<String, Object>> vouchers = new ArrayList<>();
		for (Voucher voucher : memoryRepository) {
			Map<String, Object> fields = voucher.extractFields();
			vouchers.add(fields);
		}
		return vouchers;
	}

}
