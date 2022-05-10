package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
	Voucher save(Voucher voucher);
	List<Voucher> findAll();
	void deleteAll();
	int deleteById(Long voucherId);
	Optional<Voucher> findById(Long voucherId);
	List<Voucher> findByCreatedAt(LocalDate createdAt);
	List<Voucher> findByVoucherType(VoucherType voucherType);
}
