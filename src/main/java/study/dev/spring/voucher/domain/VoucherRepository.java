package study.dev.spring.voucher.domain;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

	void save(Voucher voucher);

	Optional<Voucher> findById(String uuid);

	List<Voucher> findAll();

	void deleteById(String uuid);
}
