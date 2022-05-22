package com.programmers.order.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.programmers.order.domain.Voucher;

public interface VoucherRepository {
	Voucher insert(Voucher voucher);

	Voucher update(Voucher voucher);

	Optional<Voucher> findById(UUID voucherId);

	void deleteByVoucherId(UUID voucherId);

	void deleteAll();

	boolean isNotExist(UUID voucherId);

	long count();

	Page<Voucher> findAll(Pageable pageable, String conditions);
}
