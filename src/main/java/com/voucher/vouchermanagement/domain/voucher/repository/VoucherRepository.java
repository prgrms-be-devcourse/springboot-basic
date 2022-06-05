package com.voucher.vouchermanagement.domain.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherCriteria;

public interface VoucherRepository {

	void insert(Voucher voucher);

	List<Voucher> findAll();

	Page<Voucher> findByCriteria(VoucherCriteria criteria, Pageable pageable);

	void deleteById(UUID id);

	void deleteAll();

	Optional<Voucher> findById(UUID id);

	Voucher update(Voucher voucher);
}
