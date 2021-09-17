package com.prgrms.w3springboot.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgrms.w3springboot.voucher.Voucher;

public interface VoucherRepository {
	Optional<Voucher> findById(UUID voucherId);

	Voucher insert(Voucher voucher);

	Voucher update(Voucher voucher);

	void delete(UUID voucherId);

	List<Voucher> findAll();
}
