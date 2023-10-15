package study.dev.spring.voucher.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

	void save(Voucher voucher);

	Optional<Voucher> findById(UUID uuid);

	List<Voucher> findAll();
}
