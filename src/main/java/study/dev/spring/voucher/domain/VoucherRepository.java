package study.dev.spring.voucher.domain;

import java.util.UUID;

public interface VoucherRepository {

	void save(Voucher voucher);

	Voucher findById(UUID uuid);
}
