package com.voucher.vouchermanagement.repository.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;

public interface VoucherRepository {

	void insert(Voucher voucher);

	List<Voucher> findAll();

	List<Voucher> findByType(VoucherType type);

	List<Voucher> findByDate(LocalDateTime startAt, LocalDateTime endAt);

    List<Voucher> findByTypeAndDate(VoucherType type, LocalDateTime startAt, LocalDateTime endAt);

	void deleteById(UUID id);

	void deleteAll();

	Optional<Voucher> findById(UUID id);

	Voucher update(Voucher voucher);
}
