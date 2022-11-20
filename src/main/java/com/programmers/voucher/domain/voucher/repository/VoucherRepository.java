package com.programmers.voucher.domain.voucher.repository;

import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.voucher.model.Voucher;

public interface VoucherRepository {

	Voucher save(Voucher voucher);

	Voucher findById(UUID voucherId);

	void delete(UUID voucherId);

	List<Voucher> findAll();

	void clear();
}
