package com.programmers.voucher.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

public interface VoucherRepository {

	Voucher save(Voucher voucher);

	Voucher findById(UUID voucherId);

	void deleteById(UUID voucherId);

	List<Voucher> findBy(VoucherType voucherType, LocalDateTime startTime, LocalDateTime endTime);

	List<Voucher> findAll();

	void clear();
}
