package com.programmers.springbasic.repository.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

public interface VoucherRepository {

	Voucher insert(Voucher voucher);

	Voucher update(Voucher voucher);

	List<Voucher> findAll();

	Optional<Voucher> findById(UUID id);

	void deleteById(UUID id);

	List<Voucher> findAllById(List<UUID> voucherIds);

	List<Voucher> findByCriteria(LocalDateTime startDate, LocalDateTime endDate, VoucherType voucherType);
}
