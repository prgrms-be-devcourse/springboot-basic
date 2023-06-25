package co.programmers.voucher.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import co.programmers.voucher.Voucher.Voucher;

@Repository
@Component
public class MemoryVoucherRepository implements VoucherRepository {
	private static final MemoryVoucherRepository INSTANCE = new MemoryVoucherRepository();
	private static final List<Voucher> memoryRepository = new ArrayList<>();

	private MemoryVoucherRepository() {
	}

	public static MemoryVoucherRepository getInstance() {
		return INSTANCE;
	}

	@Override
	public void save(Voucher voucher) {
		memoryRepository.add(voucher);
	}

	@Override
	public List<Voucher> inquire() {
		return memoryRepository;
	}
}
