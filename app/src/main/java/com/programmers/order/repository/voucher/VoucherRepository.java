package com.programmers.order.repository.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.order.domain.Voucher;

public interface VoucherRepository {
	Voucher insert(Voucher voucher);

	List<Voucher> findAll();

	Optional<Voucher> findById(UUID voucherId);

	void delete(UUID voucherId);

	int exsitsByVocuher(UUID voucherId);

}
