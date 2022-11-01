package com.programmers.voucher.repository;

import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher);

	Voucher findByUUID(UUID voucherId);

	List<Voucher> findAll();

	void clear();
}
