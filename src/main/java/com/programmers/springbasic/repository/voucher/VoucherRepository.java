package com.programmers.springbasic.repository.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.springbasic.entity.voucher.Voucher;

public interface VoucherRepository {

	Voucher insert(Voucher voucher);

	Voucher update(Voucher voucher);

	List<Voucher> findAll();

	Optional<Voucher> findById(UUID id);

	void deleteById(UUID id);

}
