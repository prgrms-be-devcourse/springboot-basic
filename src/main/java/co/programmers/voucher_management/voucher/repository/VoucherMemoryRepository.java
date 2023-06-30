package co.programmers.voucher_management.voucher.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {
	private static final ArrayList<Voucher> repository = new ArrayList<>();

	private VoucherMemoryRepository() {
	}

	@Override
	public void save(Voucher voucher) {
		repository.add(voucher);
	}

	public List<Voucher> findAll() {
		List<Voucher> vouchers = new ArrayList<>();
		for (Voucher voucher : repository) {
			int id = voucher.getId();
			DiscountStrategy discountStrategy = voucher.getDiscountStrategy();
			vouchers.add(Voucher.builder()
					.id(id)
					.discountStrategy(discountStrategy)
					.build());
		}
		return vouchers;
	}

	@Override
	public int getVoucherCount() {
		return repository.size();
	}

}
