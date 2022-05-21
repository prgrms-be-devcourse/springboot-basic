package com.voucher.vouchermanagement.domain.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherCriteria;

public interface VoucherRepository {

	void insert(Voucher voucher);

	List<Voucher> findAll();

	List<Voucher> findByCriteria(VoucherCriteria criteria);

	void deleteById(UUID id);

	void deleteAll();

	Optional<Voucher> findById(UUID id);

	Voucher update(Voucher voucher);
}
