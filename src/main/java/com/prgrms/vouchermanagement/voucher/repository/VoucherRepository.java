package com.prgrms.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public interface VoucherRepository {
	List<Voucher> findAll();

	Voucher insert(Voucher voucher);

	Optional<Voucher> findById(UUID id);

	boolean deleteById(UUID id);

	boolean deleteAll();
}
