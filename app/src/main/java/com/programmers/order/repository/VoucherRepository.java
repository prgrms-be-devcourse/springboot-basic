package com.programmers.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.order.domain.Voucher;

public interface VoucherRepository {
	Voucher insert(Voucher voucher);

	Voucher update(Voucher voucher);

	Optional<Voucher> findById(UUID voucherId);

	List<Voucher> findAll();

	void deleteByVoucherId(UUID voucherId);

	void deleteAll();

	boolean isNotExist(UUID voucherId);
}
