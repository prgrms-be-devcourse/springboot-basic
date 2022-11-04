package com.programmers.voucher.repository.voucher;

import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.voucher.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher);

	Voucher findByUUID(UUID voucherId);

	List<Voucher> findAll();

	void clear();
}
